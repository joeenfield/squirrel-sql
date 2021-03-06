/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*        Metouia Look And Feel: a free pluggable look and feel for java        *
*                         http://mlf.sourceforge.net                           *
*          (C) Copyright 2002, by Taoufik Romdhane and Contributors.           *
*                                                                              *
*   This library is free software; you can redistribute it and/or modify it    *
*   under the terms of the GNU Lesser General Public License as published by   *
*   the Free Software Foundation; either version 2.1 of the License, or (at    *
*   your option) any later version.                                            *
*                                                                              *
*   This library is distributed in the hope that it will be useful,            *
*   but WITHOUT ANY WARRANTY; without even the implied warranty of             *
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                       *
*   See the GNU Lesser General Public License for more details.                *
*                                                                              *
*   You should have received a copy of the GNU General Public License along    *
*   with this program; if not, write to the Free Software Foundation, Inc.,    *
*   59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.                    *
*                                                                              *
*  MetouiaToggleButtonUI.java                                                  *
*   Original Author:  Taoufik Romdhane                                         *
*   Contributor(s):                                                            *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package net.sourceforge.mlf.metouia;

import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.ButtonModel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToggleButtonUI;

/**
 * This class represents the UI delegate for the JToggleButton component.
 *
 * @author Taoufik Romdhane
 */
public class MetouiaToggleButtonUI extends MetalToggleButtonUI
{

  /**
   * The Cached UI delegate.
   */
  private static final MetouiaToggleButtonUI buttonUI =
    new MetouiaToggleButtonUI();

  /**
   * Creates the UI delegate for the given component.
   *
   * @param c The component to create its UI delegate.
   * @return The UI delegate for the given component.
   */
  public static ComponentUI createUI(JComponent c)
  {
    return buttonUI;
  }

  /**
   * Paints the given component.
   *
   * @param g The graphics context to use.
   * @param c The component to paint.
   */
  public void paint(Graphics g, JComponent c)
  {
    super.paint(g, c);
    AbstractButton button = (AbstractButton)c;
    ButtonModel model = button.getModel();

    // Don't paint gradients on pressed buttons!
    if (!model.isPressed() && !model.isSelected())
    {
      // If the button is on a toolbar and the mouse is over, then paint the
      // gradients.
      if (button.isContentAreaFilled() || button.getModel().isRollover())
      {
        // Paint the horizontal highlight gradient:
        MetouiaGradients.drawHorizontalHighlight(g, c);

        // Paint the horizontal shadow gradient:
        MetouiaGradients.drawHorizontalShadow(g, c);
      }
    }
  }

  /**
   * Paints the background of given component whenit is pressed.
   *
   * @param g The graphics context to use.
   * @param button The button to paint its background.
   */
  protected void paintButtonPressed(Graphics g, AbstractButton button)
  {
    if (button.isContentAreaFilled() || button.getModel().isRollover())
    {
      if (button.isSelected())
      {
        g.setColor(selectColor);
        g.fillRect(0, 0, button.getWidth(), button.getHeight());
        return;
      }
    }
  }
}