/*  1:   */ package net.makeshot.main;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  5:   */ import java.awt.Dimension;
/*  6:   */ import java.awt.Graphics2D;
/*  7:   */ import java.awt.RenderingHints;
/*  8:   */ import java.awt.image.BufferedImage;
/*  9:   */ import java.io.IOException;
/* 10:   */ import java.net.MalformedURLException;
/* 11:   */ import java.net.URL;

/* 12:   */ import javax.imageio.ImageIO;
/* 13:   */ import javax.swing.ImageIcon;
/* 14:   */ import javax.swing.JButton;
/* 15:   */ import javax.swing.JLabel;
/* 16:   */ import javax.swing.JWindow;
/* 17:   */ import javax.swing.border.LineBorder;

import net.makeshot.logs.LogError;
/* 18:   */ 
/*  4:   */ 
/* 19:   */ 
/* 20:   */ public class ToolTipPanel
/* 21:   */   extends JWindow
/* 22:   */   implements Runnable
/* 23:   */ {
/* 24:   */   static BufferedImage dimg;
/* 25:   */   private String image;
/* 26:   */   
/* 27:   */   private static BufferedImage resize(BufferedImage img)
/* 28:   */   {
/* 29:25 */     int w = img.getWidth();
/* 30:26 */     int h = img.getHeight();
/* 31:27 */     int newH = 200;
/* 32:28 */     int newW = 200;
/* 33:29 */     dimg = new BufferedImage(newW, newH, img.getType());
/* 34:30 */     Graphics2D g = dimg.createGraphics();
/* 35:31 */     g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
/* 36:32 */       RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 37:33 */     g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
/* 38:34 */     g.dispose();
/* 39:35 */     return dimg;
/* 40:   */   }
/* 41:   */   
/* 42:39 */   private JButton imgPanel = new JButton();
/* 43:   */   private JLabel lblLoadingThumbnail;
/* 44:   */   private BufferedImage thumbnail;
/* 45:   */   
/* 46:   */   private void create()
/* 47:   */   {
/* 48:45 */     setPreferredSize(new Dimension(200, 200));
/* 49:46 */     setMinimumSize(getPreferredSize());
/* 50:47 */     setMaximumSize(getPreferredSize());
/* 51:   */     
/* 52:49 */     getContentPane().add(this.lblLoadingThumbnail);
/* 53:50 */     this.lblLoadingThumbnail.setLocation(getHeight() / 2, getWidth() / 2);
/* 54:51 */     this.imgPanel.setBorder(new LineBorder(Color.BLACK, 1));
/* 55:52 */     new Thread(this).start();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void exit()
/* 59:   */   {
/* 60:56 */     dispose();
/* 61:57 */     if (this.thumbnail != null) {
/* 62:58 */       this.thumbnail.flush();
/* 63:   */     }
/* 64:60 */     if (dimg != null) {
/* 65:61 */       dimg.flush();
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   @Override
public void run()
/* 70:   */   {
/* 71:   */     try
/* 72:   */     {
/* 73:68 */       this.thumbnail = ImageIO.read(new URL(this.image));
/* 74:69 */       if ((this.thumbnail.getWidth() > 200) || (this.thumbnail.getHeight() > 200)) {
/* 75:70 */         this.thumbnail = resize(this.thumbnail);
/* 76:   */       }
/* 77:73 */       this.imgPanel.setIcon(new ImageIcon(this.thumbnail));
/* 78:74 */       this.imgPanel.setBounds(0, 0, this.thumbnail.getWidth(), 
/* 79:75 */         this.thumbnail.getHeight());
/* 80:76 */       this.lblLoadingThumbnail.setText("");
/* 81:77 */       getContentPane().add(this.imgPanel);
/* 82:   */     }
/* 83:   */     catch (MalformedURLException e)
/* 84:   */     {
/* 85:80 */       LogError.get(e);
/* 86:   */     }
/* 87:   */     catch (IOException e)
/* 88:   */     {
/* 89:82 */       LogError.get(e);
/* 90:   */     }
/* 91:   */   }
/* 92:   */   
/* 93:   */   public void showPanel(int x, int y, String link)
/* 94:   */   {
/* 95:87 */     this.image = link;
/* 96:88 */     this.thumbnail = null;
/* 97:89 */     this.lblLoadingThumbnail = new JLabel();
/* 98:90 */     this.imgPanel.setIcon(null);
/* 99:91 */     this.lblLoadingThumbnail.setText("Loading thumbnail");
/* :0:92 */     exit();
/* :1:93 */     create();
/* :2:94 */     setLocation(x + 230, y);
/* :3:95 */     setVisible(true);
/* :4:   */   }
/* :5:   */ }

