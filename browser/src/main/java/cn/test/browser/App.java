package cn.test.browser;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import cn.test.browser.s.AppUtil;
import cn.test.browser.s.SQLiteService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class App extends JPanel {

    private static final long serialVersionUID = 4827460619063247128L;

    private JTabbedPane tabbedPane;

    //
    public JTabbedPane getTabbedPane() {
        return this.tabbedPane;
    }
    //

    public App(String url) {
        super(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setBarsVisible(true);
        webBrowser.setButtonBarVisible(true);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setStatusBarVisible(true);
        webBrowser.setLocationBarVisible(true);

        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addTab("起始页", webBrowser);
        AppUtil.addWebBrowserListener(tabbedPane, webBrowser);
        webBrowser.navigate(url);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
//        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
        // webBrowser.executeJavascript("javascrpit:window.location.href='http://www.baidu.com'");
    }

    public static void main(String[] args) {
        final String url = "http://www.baidu.com/";
        final String title = "浏览器";
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SQLiteService.getInstance().postConstruct();

                final JFrame frame = new JFrame(title);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setUndecorated(false);
                URL resource = getClass().getResource("/cn/test/browser/app.png");
                ImageIcon icon = new ImageIcon(resource);
                frame.setIconImage(icon.getImage());

                final Container contentPane = frame.getContentPane();

                App app = new App(url);
                JTabbedPane tabbedPane = app.getTabbedPane();
                contentPane.add(app, BorderLayout.CENTER);
                // ======================
                AppUtil.initMenu(frame, contentPane);
                AppUtil.initTabMenu(tabbedPane);
                // ======================
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
