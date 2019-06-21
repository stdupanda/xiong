package cn.test.browser;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class App extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel webBrowserPanel;

    private JWebBrowser webBrowser;

    private String url;

    public App(String url) {
        super(new BorderLayout());
        this.url = url;

        webBrowserPanel = new JPanel(new BorderLayout());
        webBrowser = new JWebBrowser();
        webBrowser.navigate(url);
        webBrowser.setButtonBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setBarsVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        // webBrowser.executeJavascript("javascrpit:window.location.href='http://www.baidu.com'");
    }

    public static void main(String[] args) {
        final String url = "http://www.baidu.com/";
        final String title = "百度";
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(title);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("我的浏览器 (゜-゜)つロ");
                frame.setUndecorated(false);
                App ieBrower = new App(url);
                frame.getContentPane().add(ieBrower, BorderLayout.CENTER);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationByPlatform(true);
                frame.setSize(1024, 768);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();
    }
}
