/*
 * Created by SharpDevelop.
 * User: Administrator
 * Date: 2019-8-7
 * Time: 9:42
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace stopapp
{
    /// <summary>
    /// Description of MainForm.
    /// </summary>
    public partial class MainForm : Form
    {
        public MainForm()
        {
            //
            // The InitializeComponent() call is required for Windows Forms designer support.
            //
            InitializeComponent();

            //
            // TODO: Add constructor code after the InitializeComponent() call.
            //
            //			textBox1.AutoSize=false;
            //			textBox1.Height=60;
        }

        void StopBtnClick(object sender, EventArgs e)
        {
            if (textBox1.Text.Trim().Equals(""))
            {
                label1.Text = "please enter param, like 'java'" + System.Environment.NewLine + System.DateTime.Now;
                return;
            }
            string tmp = "";
            if (checkBox1.Checked)
            {
                tmp = "* ";
            }
            string command = " taskkill /f /im " + textBox1.Text.Trim() + tmp;
            System.Diagnostics.Process p = new System.Diagnostics.Process();
            p.StartInfo.FileName = "cmd.exe";           //設定程序名
            p.StartInfo.Arguments = "/c " + command;    //設定程式執行參數
            p.StartInfo.UseShellExecute = false;        //關閉Shell的使用
            p.StartInfo.RedirectStandardInput = true;   //重定向標準輸入
            p.StartInfo.RedirectStandardOutput = true;  //重定向標準輸出
            p.StartInfo.RedirectStandardError = true;   //重定向錯誤輸出
            p.StartInfo.CreateNoWindow = true;          //設置不顯示窗口

            p.Start();   //啟動
            p.StandardInput.WriteLine(command);       //也可以用這種方式輸入要執行的命令
            string s = p.StandardOutput.ReadToEnd();
            s += p.StandardError.ReadToEnd();
            label1.Text = s;
            p.StandardInput.WriteLine("exit");        //不過要記得加上Exit要不然下一行程式執行的時候會當機
        }
        [DllImport("User32.dll")]
        private static extern bool SetCursorPos(int x, int y);
        private void TextBox1KeyEvent(object sender, KeyEventArgs e)
        {
            //MessageBox.Show("" + e.KeyValue);
            if (e.KeyValue == 27)
            {
                Application.Exit();
                return;
            }
            if (e.KeyValue == 13)
            {
                stopBtn.PerformClick();
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (checkBox2.Checked)
            {
                int x = Control.MousePosition.X;
                int y = Control.MousePosition.Y;
                if (y > 600 || y < 100)
                {
                    y = 50;
                }
                SetCursorPos(x, y + 6);
            }
        }
    }
}
