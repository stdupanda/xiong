package cn.test.browser;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class AppMenu {

    public static void initMenu(final JFrame frame, final Container contentPane) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu menu1 = new JMenu("账户");
        menuBar.add(menu1);
        
        JMenuItem itemAccout = new JMenuItem("登录");
        itemAccout.setHorizontalAlignment(SwingConstants.CENTER);
        
        JMenuItem menuItem_2 = new JMenuItem("注册");
        menuItem_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(1);
                Component[] components = contentPane.getComponents();
                App app = (App) contentPane.getComponent(0);
                JWebBrowser jWebBrowser = app.getWebBrowser();
                System.out.println(jWebBrowser);
                System.out.println(components);
            }
        });
        
        JMenu menu2 = new JMenu("收藏夹");
        
        JMenuItem menuItem_3 = new JMenuItem("收藏当前页面");
        menuItem_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "简单浏览器", "关于", JOptionPane.PLAIN_MESSAGE);
                App app = (App) contentPane.getComponent(0);
                JWebBrowser jWebBrowser = app.getWebBrowser();
                System.out.println(jWebBrowser.getResourceLocation());
                System.out.println(jWebBrowser.getPageTitle());
            }
        });
        menu2.add(menuItem_3);
        
        JMenuItem menuItem_1 = new JMenuItem("收藏1");
        menu1.add(itemAccout);
        menu1.add(menuItem_2);
        
        menu1.add(new JSeparator());
        
        JMenuItem menuItem_4 = new JMenuItem("注销");
        menu1.add(menuItem_4);
        menuBar.add(menu2);
        
        JSeparator separator = new JSeparator();
        menu2.add(separator);
        menu2.add(menuItem_1);
        
        JMenu menu = new JMenu("帮助");
        menuBar.add(menu);
        
        JMenuItem menuItem = new JMenuItem("关于");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "简单浏览器", "关于", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menu.add(menuItem);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        menuBar.add(separator_1);
        
        JLabel lblNewLabel = new JLabel("未登录");
        menuBar.add(lblNewLabel);
    }
}
