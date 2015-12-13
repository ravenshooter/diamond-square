/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Insets;
import java.awt.Panel;

    public class BorderPanel extends Panel{
        private Insets insets;

        public BorderPanel(int inset) {
            insets = new Insets(inset, inset, inset, inset);
        }

        /**
         * 
         * @param north
         * @param east
         * @param south
         * @param west 
         */
        public BorderPanel(int north, int east, int south, int west) {
            insets = new Insets(north, east, south, west);
        }

        @Override
        public Insets getInsets() {
            return insets;
        }


    }