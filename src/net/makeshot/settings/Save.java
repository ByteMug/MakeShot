package net.makeshot.settings;

import java.io.File;

import makeshot.ini.Writer;

import org.ini4j.Reg;
import org.ini4j.Registry;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
public class Save
{
  String autostartKey = "HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run";
  Registry.Key key;
  File path = new File(Static.appDir + "MakeShot.exe");
  Reg reg = new Reg();
  Writer write = new Writer();
  
  Save(MainSettings ms, Settings s, Upload up)
  {
    new Writer().hotkeys();
    if (HotKeys.chckbxEnableHotkeys.isSelected()) {
      this.write.settings("hotkeys", "1");
    } else {
      this.write.settings("hotkeys", "2");
    }
    if (ms.chckbxSound.isSelected()) {
      this.write.settings("sound", "1");
    } else {
      this.write.settings("sound", "2");
    }
    if (ms.chckbxTooltip.isSelected()) {
      this.write.settings("tooltip", "1");
    } else {
      this.write.settings("tooltip", "2");
    }
    if (ms.chckbxEdit.isSelected()) {
      this.write.settings("edit", "1");
    } else {
      this.write.settings("edit", "2");
    }
    if (ms.chckbxCopyLink.isSelected()) {
      this.write.settings("copyLink", "1");
    } else {
      this.write.settings("copyLink", "2");
    }
    if (up.chckbxUpload.isSelected()) {
      this.write.settings("upload", "1");
    } else {
      this.write.settings("upload", "2");
    }
    int zupa = ((Integer)MainSettings.timeoutInput.getValue()).intValue();
    if (zupa < 250)
    {
      this.write.settings("timeout", "250");
      MainSettings.timeoutInput.setValue(Integer.valueOf(Static.timeout));
    }
    else if (zupa > 25000)
    {
      MainSettings.timeoutInput.setValue(Integer.valueOf(Static.timeout));
      this.write.settings("timeout", "25000");
    }
    else
    {
      this.write.settings("timeout", 
        Integer.toString(((Integer)MainSettings.timeoutInput.getValue()).intValue()));
    }
    if (s.chckbxSaveIn.isSelected())
    {
      this.write.settings("save", "1");
      this.write.settings("path", s.textField.getText());
    }
    else
    {
      this.write.settings("save", "2");
    }
    if (ms.chckbxStartWithSystem.isSelected())
    {
      Static.startWithSystem = 1;
      Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, 
        "Software\\Microsoft\\Windows\\CurrentVersion\\Run", 
        "MakeShot", "\"" + this.path.toString() + "\"");
    }
    else if (Advapi32Util.registryValueExists(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "MakeShot"))
    {
      Static.startWithSystem = 0;
      Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER, 
        "Software\\Microsoft\\Windows\\CurrentVersion\\Run", 
        "MakeShot");
    }
    this.write.settings("ext", String.valueOf(s.comboBox.getSelectedItem()));
    this.write.settings("uploadTo", 
      String.valueOf(up.comboBox.getSelectedItem()));
    if (up.comboBox.getSelectedItem().equals("FTP server"))
    {
      this.write.ftpserv("Login", up.txtLogin.getText());
      this.write.ftpserv("Pass", new String(up.txtPass.getPassword()));
      this.write.ftpserv("Dir", up.txtDir.getText());
      this.write.ftpserv("Server", up.txtServ.getText());
      this.write.ftpserv("Port", up.txtPort.getText());
      this.write.ftpserv("URL", up.ftpUrl.getText());
    }
    Static.update();
  }
}
