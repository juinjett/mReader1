/**
 *  Author :  hmg25
 *  Description :
 */
package com.mmclub.mreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Vector;

import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.mmclub.mreader.SettingActivity;
import com.mmclub.mreader.OpenCharpter;

public class BookPageFactory {

	private File book_file = null;
	private MappedByteBuffer m_mbBuf = null;
	private int m_mbBufLen = 0;
	private int m_mbBufBegin = 0;
	private int m_mbBufEnd = 0;
	private String m_strCharsetName = "GBK";
	private Bitmap m_book_bg = null;
	private int mWidth;
	private int mHeight;

	private Vector<String> m_lines = new Vector<String>();
	private int m_fontSize;
	private int m_textColor;
	private int m_backColor = 0xffff9e85; // Background color
	private int marginWidth = 15;
	private int marginHeight = 20;

	private int mLineCount; //line count per page
	private float mVisibleHeight; //page height
	private float mVisibleWidth; // page width
	private boolean m_isfirstPage,m_islastPage;

	
	// private int m_nLineSpaceing = 5;
	
	private Paint mPaint;

	public BookPageFactory(int w, int h, int textSize, int textColor) {
		// TODO Auto-generated constructor stub
		mWidth = w;
		mHeight = h;
		m_fontSize = textSize;
		m_textColor = textColor;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setTextSize(m_fontSize);
		mPaint.setColor(m_textColor);
		mVisibleWidth = mWidth - marginWidth * 2;
		mVisibleHeight = mHeight - marginHeight * 2;
		mLineCount = (int) (((mVisibleHeight / m_fontSize)-1) /1.5 ); // Number of lines visible
	}

	public void openbook(String strFilePath) throws IOException {
		book_file = new File(strFilePath);
		long lLen = book_file.length();
		m_mbBufLen = (int) lLen;
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel().map(
				FileChannel.MapMode.READ_ONLY, 0, lLen);
	}


	
	public void opencharpter(String strFilePath, int charpter) throws IOException{
		openbook(strFilePath);
		switch(charpter){
		case 0:
			m_mbBufBegin = 0;
			m_mbBufEnd = 0;
			break;
		
		case 1:
			m_mbBufBegin = 4562;
			m_mbBufEnd = 4750;
			break;
			
		case 2:	
			m_mbBufBegin = 66781;
			m_mbBufEnd = 66969;
			break;
			
		case 3:	
			m_mbBufBegin = 128486;
			m_mbBufEnd = 128630;
			break;
			
		case 4:	
			m_mbBufBegin = 190062;
			m_mbBufEnd = 190206;
			break;	
			
		case 5:	
			m_mbBufBegin = 229569;
			m_mbBufEnd = 229733;
			break;	
			
		case 6:	
			m_mbBufBegin = 278331;
			m_mbBufEnd = 278489;
			break;
			
		case 7:	
			m_mbBufBegin = 325148;
			m_mbBufEnd = 325290;
			break;		
			
		case 8:	
			m_mbBufBegin = 361963;
			m_mbBufEnd = 362121;
			break;	
			
		case 9:	
			m_mbBufBegin = 406675;
			m_mbBufEnd = 406829;
			break;		
			
		case 10:	
			m_mbBufBegin = 458305;
			m_mbBufEnd = 458445;
			break;
			
		case 11:	
			m_mbBufBegin = 511764;
			m_mbBufEnd = 511914;
			break;
			
		case 12:	
			m_mbBufBegin = 548507;
			m_mbBufEnd = 548659;
			break;
		
		case 13:	
			m_mbBufBegin = 584566;
			m_mbBufEnd = 584706;
			break;
			
		case 14:	
			m_mbBufBegin = 609728;
			m_mbBufEnd = 609886;
			break;	
			
		case 15:	
			m_mbBufBegin = 664657;
			m_mbBufEnd = 664787;
			break;		
		}
	}

	public void openbymark(String strFilePath,int begin,int end) throws IOException {
		book_file = new File(strFilePath);
		long lLen = book_file.length();
		m_mbBufLen = (int) lLen;
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel().map(
				FileChannel.MapMode.READ_ONLY, 0, lLen);
		m_mbBufBegin = begin;
		m_mbBufEnd = end;
	}

	
	protected byte[] readParagraphBack(int nFromPos) {
		int nEnd = nFromPos;
		int i;
		byte b0, b1;
		if (m_strCharsetName.equals("UTF-16LE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}

		} else if (m_strCharsetName.equals("UTF-16BE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}
		} else {
			i = nEnd - 1;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				if (b0 == 0x0a && i != nEnd - 1) {
					i++;
					break;
				}
				i--;
			}
		}
		if (i < 0)
			i = 0;
		int nParaSize = nEnd - i;
		int j;
		byte[] buf = new byte[nParaSize];
		for (j = 0; j < nParaSize; j++) {
			buf[j] = m_mbBuf.get(i + j);
		}
		return buf;
	}


	// read previous paragraph
	protected byte[] readParagraphForward(int nFromPos) {
		int nStart = nFromPos;
		int i = nStart;
		byte b0, b1;
		// Change lines based on encoding
		if (m_strCharsetName.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetName.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (b0 == 0x0a) {
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		return buf;
	}

	protected Vector<String> pageDown() {
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen) {
			byte[] paraBuf = readParagraphForward(m_mbBufEnd); // read a paragraph
			m_mbBufEnd += paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String strReturn = "";
			if (strParagraph.indexOf("\r\n") != -1) {
				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1) {
				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			}

			if (strParagraph.length() == 0) {
				lines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				lines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
				if (lines.size() >= mLineCount) {
					break;
				}
			}
			if (strParagraph.length() != 0) {
				try {
					m_mbBufEnd -= (strParagraph + strReturn)
							.getBytes(m_strCharsetName).length;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return lines;
	}

	protected void pageUp() {
		if (m_mbBufBegin < 0)
			m_mbBufBegin = 0;
		Vector<String> lines = new Vector<String>();
		String strParagraph = "";
		while (lines.size() < mLineCount && m_mbBufBegin > 0) {
			Vector<String> paraLines = new Vector<String>();
			byte[] paraBuf = readParagraphBack(m_mbBufBegin);
			m_mbBufBegin -= paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "");
			strParagraph = strParagraph.replaceAll("\n", "");

			if (strParagraph.length() == 0) {
				paraLines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				paraLines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
			}
			lines.addAll(0, paraLines);
		}
		while (lines.size() > mLineCount) {
			try {
				m_mbBufBegin += lines.get(0).getBytes(m_strCharsetName).length;
				lines.remove(0);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		m_mbBufEnd = m_mbBufBegin;
		return;
	}

	protected void prePage() throws IOException {
		if (m_mbBufBegin <= 0) {
			m_mbBufBegin = 0;
			m_isfirstPage=true;
			return;
		}else m_isfirstPage=false;
		m_lines.clear();
		pageUp();
		m_lines = pageDown();
	}

	public void nextPage() throws IOException {
		if (m_mbBufEnd >= m_mbBufLen) {
			m_islastPage=true;
			return;
		}else m_islastPage=false;
		m_lines.clear();
		m_mbBufBegin = m_mbBufEnd;
		m_lines = pageDown();
	}
	
	public void onDraw(Canvas c) {
		if (m_lines.size() == 0)
			m_lines = pageDown();
		if (m_lines.size() > 0) {
			if (m_book_bg == null)
				c.drawColor(m_backColor);
			else
				c.drawBitmap(m_book_bg, 0, 0, null);
			float y = marginHeight;
			for (String strLine : m_lines) {
				y += m_fontSize*1.5;
				c.drawText(strLine, marginWidth, y, mPaint);
			}
		}
		float fPercent = (float) (m_mbBufBegin * 1.0 / m_mbBufLen);
		DecimalFormat df = new DecimalFormat("#0.0");
		String strPercent = df.format(fPercent * 100) + "%";		
		int nPercentWidth = (int) mPaint.measureText("999.9%") + 1;
		c.drawText(strPercent, mWidth - nPercentWidth, mHeight - 5, mPaint);
//		return fPercent;
	}

	public void setBgBitmap(Bitmap BG) {
		m_book_bg = BG;
	}
	
	public Bitmap reBip(){
		return m_book_bg;
	}
	
	public boolean isfirstPage() {
		return m_isfirstPage;
	}
	public boolean islastPage() {
		return m_islastPage;
	}
	
	public void changeFontStyle(int FontSize, int FontColor){
		m_fontSize = FontSize;
		m_textColor = FontColor;
		mPaint.setTextSize(m_fontSize);
		mPaint.setColor(m_textColor);
	}

	public int sendBegin(){
		return m_mbBufBegin;
	}
	
	public int sendEnd(){
		return m_mbBufEnd;
	}
}


