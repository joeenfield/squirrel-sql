package org.squirrelsql.session.sql;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.squirrelsql.services.Utils;
import org.squirrelsql.session.SessionTabContext;
import org.squirrelsql.session.TokenAtCarretInfo;
import org.squirrelsql.session.parser.ParserEventsListener;
import org.squirrelsql.session.parser.ParserEventsProcessor;
import org.squirrelsql.session.parser.kernel.ErrorInfo;
import org.squirrelsql.session.parser.kernel.TableAliasInfo;
import org.squirrelsql.session.schemainfo.SchemaCacheProperty;
import org.squirrelsql.session.sql.syntax.*;
import org.squirrelsql.workaround.CodeAreaRepaintWA;
import org.squirrelsql.workaround.FocusSqlTextAreaWA;

public class SQLTextAreaServices
{

   private final CodeArea _sqlTextArea;
   private final SQLSyntaxHighlighting _sqlSyntaxHighlighting;
   private final ParserEventsProcessor _parserEventsProcessor;
   private LexAndParseResultListener _lexAndParseResultListener;

   private CaretPopup _caretPopup;


   public SQLTextAreaServices(SessionTabContext sessionTabContext)
   {
      _sqlTextArea = new CodeArea();

      SchemaCacheProperty schemaCacheValue = sessionTabContext.getSession().getSchemaCacheValue();

      _parserEventsProcessor = new ParserEventsProcessor(this, sessionTabContext.getSession());

      _parserEventsProcessor.addParserEventsListener(new ParserEventsListener()
      {
         @Override
         public void aliasesFound(TableAliasInfo[] aliasInfos)
         {
            onAliasesFound(aliasInfos);
         }

         @Override
         public void errorsFound(ErrorInfo[] errorInfos)
         {
            _sqlSyntaxHighlighting.setErrorInfos(errorInfos);
         }
      });

      _sqlSyntaxHighlighting = new SQLSyntaxHighlighting(_sqlTextArea, new SQLSyntaxHighlightTokenMatcher(schemaCacheValue), schemaCacheValue);

      new CtrlLeftRightHandler(_sqlTextArea);

      schemaCacheValue.addListener(() -> updateHighlighting());

      _caretPopup = new CaretPopup(_sqlTextArea);
   }

   public void updateHighlighting()
   {
      _sqlSyntaxHighlighting.updateHighlighting();
      _parserEventsProcessor.triggerParser();
   }

   private void onAliasesFound(TableAliasInfo[] aliasInfos)
   {
      if(null != _lexAndParseResultListener)
      {
         _lexAndParseResultListener.aliasesFound(aliasInfos);
      }
   }

   public CodeArea getTextArea()
   {
      return _sqlTextArea;
   }

   public void setOnKeyPressed(EventHandler<KeyEvent> keyEventHandler)
   {
      _sqlTextArea.setOnKeyPressed(keyEventHandler);

   }

   public String getCurrentSql()
   {
      String sql = _sqlTextArea.getSelectedText();

      if(Utils.isEmptyString(sql))
      {
         int caretPosition = _sqlTextArea.getCaretPosition();
         String sqlTextAreaText = _sqlTextArea.getText();

         String sep = SyntaxConstants.CODE_AREA_LINE_SEP + SyntaxConstants.CODE_AREA_LINE_SEP;

         int begin = caretPosition;
         while(0 < begin && false == sqlTextAreaText.substring(0, begin).endsWith(sep))
         {
            --begin;
         }

         int end = caretPosition;
         while(sqlTextAreaText.length() > end && false == sqlTextAreaText.substring(end).startsWith(sep))
         {
            ++end;
         }

         sql = sqlTextAreaText.substring(begin, end);
      }

      return sql;
   }

   public String getTokenAtCarret()
   {
      return _getTokenAtCarretInfo().getTokenTillCarret();

   }

   private TokenAtCarretInfo _getTokenAtCarretInfo()
   {
      int caretPosition = _sqlTextArea.getCaretPosition();
      String sqlTextAreaText = _sqlTextArea.getText();


      int begin = caretPosition-1;
      while(0 < begin && false == Character.isWhitespace(sqlTextAreaText.charAt(begin)))
      {
         --begin;
      }

      if(0 < begin)
      {
         begin +=1; // keep the whitespace
      }

      int end = caretPosition;
      while(sqlTextAreaText.length() - 1 >= end && false == Character.isWhitespace(sqlTextAreaText.charAt(end)))
      {
         ++end;
      }

      begin = Math.max(begin, 0);
      String token = sqlTextAreaText.substring(begin, caretPosition).trim();

      return new TokenAtCarretInfo(token, begin, end, caretPosition);
   }

   public void replaceTokenAtCarretBy(String replacement)
   {
      replaceTokenAtCarretBy(0, false, replacement);
   }


   public void replaceTokenAtCarretBy(int offset, boolean removeSucceedingChars, String replacement)
   {
      TokenAtCarretInfo tci = _getTokenAtCarretInfo();

      if (removeSucceedingChars)
      {
         _sqlTextArea.replaceText(tci.getTokenBeginPos() + offset, tci.getTokenEndPos(), replacement);
      }
      else
      {
         _sqlTextArea.replaceText(tci.getTokenBeginPos() + offset, tci.getCaretPosition(), replacement);
      }
      CodeAreaRepaintWA.avoidRepaintProblemsAfterTextModification(_sqlTextArea);
   }

   public double getFontHight()
   {
      if( 0 > _sqlTextArea.getFont().getSize())
      {
         return 12;
      }
      else
      {
         return _sqlTextArea.getFont().getSize();
      }
   }

   public void requestFocus()
   {
      FocusSqlTextAreaWA.forceFocus(_sqlTextArea);
   }

   public double getStringWidth(String str)
   {
      FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(_sqlTextArea.getFont());
      return fontMetrics.computeStringWidth(str);
   }

   public void setLexAndParseResultListener(LexAndParseResultListener lexAndParseResultListener)
   {
      _lexAndParseResultListener = lexAndParseResultListener;
      _sqlSyntaxHighlighting.setLexAndParseResultListener(lexAndParseResultListener);
   }

   public void close()
   {
      _parserEventsProcessor.endProcessing();
   }

   public void appendToEditor(String sql)
   {
      _sqlTextArea.appendText(sql);
      CodeAreaRepaintWA.avoidRepaintProblemsAfterTextModification(_sqlTextArea);

   }

   public javafx.scene.text.Font getFont()
   {
      return _sqlTextArea.getFont();
   }

   public void insertAtCarret(String s)
   {
      int caretPosition = _sqlTextArea.getCaretPosition();
      _sqlTextArea.replaceText(caretPosition,caretPosition,s);
      CodeAreaRepaintWA.avoidRepaintProblemsAfterTextModification(_sqlTextArea);
   }

   public CaretPopup getCaretPopup()
   {
      return _caretPopup;
   }
}
