package cn.test.browser;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.*;
import java.awt.*;

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
        webBrowser.setBarsVisible(true);
        webBrowser.setButtonBarVisible(true);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setStatusBarVisible(true);
        webBrowser.setLocationBarVisible(true);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        // webBrowser.executeJavascript("javascrpit:window.location.href='http://www.baidu.com'");
    }

    public JWebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    public static void main(String[] args) {
        final String url = "http://www.baidu.com/";
        final String title = "百度";
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame frame = new JFrame(title);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("简单浏览器");
                frame.setUndecorated(false);

                final Container contentPane = frame.getContentPane();

                App app = new App(url);
                frame.getContentPane().add(app, BorderLayout.CENTER);
                //======================
                AppMenu.initMenu(frame, contentPane);
                //======================
                frame.setExtendedState(JFrame.NORMAL);
                frame.setLocationByPlatform(true);
                frame.setSize(1024, 768);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

            
        });
        NativeInterface.runEventPump();
    }
}
