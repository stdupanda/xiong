package cn.test.browser.s;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import cn.test.browser.App;
import cn.test.browser.e.Record;
import cn.test.browser.e.User;
import cn.test.browser.v.LoginDialog;
import cn.test.browser.v.RegDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppUtil {

    private static User user;

    private static final Logger log = LoggerFactory.getLogger(AppUtil.class);

    /**
     * 是否已登录
     */
    public static Boolean checkLogin() {
        return null != user;
    }

    public static void initMenu(final JFrame frame, final Container contentPane) {
        App app = (App) contentPane.getComponent(0);
        final JTabbedPane tabbedPane = app.getTabbedPane();

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu1 = new JMenu("账户");
        menuBar.add(menu1);

        JMenuItem itemAccout = new JMenuItem("登录");
        itemAccout.setHorizontalAlignment(SwingConstants.CENTER);
        itemAccout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog dialog = new LoginDialog(frame);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });

        JMenuItem menuItem_2 = new JMenuItem("注册");
        menuItem_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkLogin()) {
                    return;
                }
                RegDialog dialog = new RegDialog(frame);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });
        JMenuItem menuItem_4 = new JMenuItem("注销");
        menuItem_4.setVisible(false);
        menuItem_4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AppUtil.initLogOper(frame, null);
            }
        });

        menu1.add(itemAccout);
        menu1.add(menuItem_2);
        menu1.add(new JSeparator());
        menu1.add(menuItem_4);

        final JMenu menu2 = new JMenu("收藏夹");
        menuBar.add(menu2);

        JMenuItem menuItem_3 = new JMenuItem("收藏当前页面");
        menuItem_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!AppUtil.checkLogin()) {
                    JOptionPane.showMessageDialog(frame.getContentPane(), "请先登录！");
                    return;
                }
                // 用户已登录，写入收藏记录
                final JWebBrowser jWebBrowser = (JWebBrowser) tabbedPane.getSelectedComponent();
                String title = jWebBrowser.getPageTitle();
                String url = jWebBrowser.getResourceLocation();
                String sqlCheck = "select count(*) from record where title='" + title + "' and userId='" + user.getId()
                        + "' ";
                Integer one = SQLiteService.getInstance().count(sqlCheck);
                if (null != one && 0 < one.intValue()) {
                    JOptionPane.showMessageDialog(frame.getContentPane(), "已收藏");
                    return;
                }
                String sql = "insert into record(userId,title,url,gmtCreate) values('" + user.getId() + "','" + title
                        + "','" + url + "','" + genDate() + "')";
                SQLiteService.getInstance().exec(sql);

                JOptionPane.showMessageDialog(frame.getContentPane(), "收藏成功");
                updateFavorites(frame, menu2);
                return;
            }
        });
        JSeparator separator = new JSeparator();

        menu2.add(menuItem_3);
        menu2.add(separator);

        JMenu menu = new JMenu("帮助");
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("关于");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "简单浏览器 © 2019，v1.0　 ", "关于", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menu.add(menuItem);

        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        menuBar.add(separator_1);

        JLabel lblNewLabel = new JLabel("未登录");
        menuBar.add(lblNewLabel);
    }

    public static void initTabMenu(final JTabbedPane tabbedPane) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem mntmNewMenuItem = new JMenuItem("关闭标签页");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tabCount = tabbedPane.getTabCount();
                log.debug("标签页数量为 {} ", tabCount);
                if (2 > tabCount) {
                    log.debug("不再关闭标签页。");
                    return;
                }
                Component selectedComponent = tabbedPane.getSelectedComponent();
                tabbedPane.remove(selectedComponent);
                log.debug("关闭标签页。");
            }
        });
        popupMenu.add(mntmNewMenuItem);
        addPopupMenu(tabbedPane, popupMenu);
    }

    public static void addPopupMenu(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            private void showMenu(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    public static void addWebBrowserListener(final JTabbedPane tabbedPane, final JWebBrowser webBrowser) {
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {

            private void updateTitle(final JTabbedPane tabbedPane, final JWebBrowser webBrowser) {
                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    if (tabbedPane.getComponentAt(i) == webBrowser) {
                        String pageTitle = webBrowser.getPageTitle();
                        pageTitle = " " + pageTitle + " ";
                        tabbedPane.setTitleAt(i, pageTitle);
                        break;
                    }
                }
            }

            @Override
            public void titleChanged(WebBrowserEvent e) {
                log.debug("loading...titleChanged");
                updateTitle(tabbedPane, webBrowser);
            }

            @Override
            public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
                JWebBrowser newWebBrowser = new JWebBrowser();
                addWebBrowserListener(tabbedPane, newWebBrowser);
                tabbedPane.addTab("加载中。。。", newWebBrowser);
                tabbedPane.setSelectedComponent(newWebBrowser);
                e.setNewWebBrowser(newWebBrowser);
            }

            @Override
            public void windowOpening(WebBrowserWindowOpeningEvent e) {
                e.getWebBrowser().setMenuBarVisible(false);
            }

            @Override
            public void locationChanged(WebBrowserNavigationEvent e) {
                super.locationChanged(e);
                log.debug("loading...locationChanged");
                updateTitle(tabbedPane, webBrowser);
            }
        });
    }

    public static void initLogOper(JFrame frame, User user) {
        log.info("初始化登录状态。");
        AppUtil.user = user;
        // 账户，收藏夹，帮助，分隔，label
        JMenuBar jMenuBar = frame.getJMenuBar();
        JLabel label = (JLabel) jMenuBar.getComponent(4);
        // 登录 注册 分隔 注销
        JMenu menu0 = jMenuBar.getMenu(0);
        JMenu menu1 = jMenuBar.getMenu(1);

        if (null != user) {// 登录
            label.setText("已登录：" + user.getName() + "   ");

            menu0.getItem(0).setVisible(false);
            menu0.getItem(1).setVisible(false);
            menu0.getItem(3).setVisible(true);

            updateFavorites(frame, menu1);
        } else {
            label.setText("未登录    ");

            menu0.getMenuComponent(0).setVisible(true);
            menu0.getMenuComponent(1).setVisible(true);
            menu0.getMenuComponent(3).setVisible(false);

            clearFavorites(menu1);
        }
    }

    private static void clearFavorites(JMenu menu) {
        int itemCount = menu.getMenuComponentCount();
        if (2 > itemCount) {
            return;
        }

        for (int i = itemCount - 1; i > 1; i--) {
            menu.remove(i);
        }
    }

    private static void updateFavorites(final JFrame frame, JMenu menu) {
        clearFavorites(menu);
        String sql = "select * from record where userId=" + user.getId() + " ";
        List<Record> list = SQLiteService.getInstance().selectList(sql, Record.class);

        int itemCount = menu.getMenuComponentCount();
        if (2 > itemCount) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            final Record record = list.get(i);
            JMenuItem menuItem = new JMenuItem(record.getTitle());
            menuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    App app = (App) frame.getContentPane().getComponent(0);
                    JTabbedPane tabbedPane = app.getTabbedPane();
                    final JWebBrowser jWebBrowser = (JWebBrowser) tabbedPane.getSelectedComponent();
                    jWebBrowser.navigate(record.getUrl());
                }
            });
            menu.add(menuItem);
        }
        log.debug("已更新收藏夹");
    }

    public static String genDate() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}
