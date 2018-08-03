package com.platform.util.otherWxUtil;

import com.platform.util.otherWxUtil.XmlUtil.XStreamFactroy;
import com.platform.entity.wxMsg.resp.*;
import com.platform.entity.wxMsg.resp.RespImageMsg.Image;
import com.platform.entity.wxMsg.resp.RespMusicMsg.Music;
import com.platform.entity.wxMsg.resp.RespNewsMsg.Articles;
import com.platform.entity.wxMsg.resp.RespNewsMsg.Articles.Item;
import com.platform.entity.wxMsg.resp.RespVideoMsg.Video;
import com.platform.entity.wxMsg.resp.RespVoiceMsg.Voice;
import com.platform.entity.wxPayReq.AbstractPayParams;
import com.thoughtworks.xstream.XStream;

/**
 * 默认请求消息处理类
 * 
 * @author phil
 * 
 */
public class MsgUtil {

	/**
	 * 将java对象转换为xml
	 * 
	 * @param msg
	 * @return
	 */
	public static String msgToXml(RespAbstractMsg msg) {
		String result = "";
		if (msg != null) {
			XStream xs = XStreamFactroy.init(true);
			xs.alias("xml", msg.getClass());
			result = xs.toXML(msg);
		}
		return result;
	}

	/**
	 * 文本消息
	 * 
	 * @param text
	 * @return
	 */
	public static String textMsgToXml(RespTextMsg text) {
		String result = "";
		if (text != null) {
			XStream xs = XStreamFactroy.init(true);
			xs.alias("xml", RespTextMsg.class);
			result = xs.toXML(text);
		}
		return result;
	}

	/**
	 * 图片消息
	 * 
	 * @param image
	 * @return
	 */
	public static String imageMsgToXml(RespImageMsg image) {
		String result = "";
		if (image != null) {
			XStream xs = XStreamFactroy.init(true);
			xs.alias("xml", RespImageMsg.class);
			xs.aliasField("Image", Image.class, "image");
			result = xs.toXML(image);
		}
		return result;
	}

	/**
	 * 音乐消息
	 * 
	 * @param music
	 * @return
	 */
	public static String musicMsgToXml(RespMusicMsg music) {
		String result = "";
		if (music != null) {
			XStream xs = XStreamFactroy.init(true);
			xs.alias("xml", RespMusicMsg.class);
			xs.aliasField("Music", Music.class, "music");
			result = xs.toXML(music);
		}
		return result;
	}

	/**
	 * 图文消息
	 * 
	 * @param news
	 * @return
	 */
	public static String newsMsgToXml(RespNewsMsg news) {
		String result = "";
		if (news != null) {
			XStream xs = XStreamFactroy.init(true);
			xs.alias("xml", RespNewsMsg.class);
			xs.addImplicitCollection(Articles.class, "list", "item", Item.class);
			xs.aliasField("Articles", RespNewsMsg.class, "articles");
			result = xs.toXML(news);
		}
		return result;
	}

	/**
	 * 视频消息
	 * 
	 * @param news
	 * @return
	 */
	public static String videoMsgToXml(RespVideoMsg video) {
		String result = "";
		if (video != null) {
			XStream xs = XStreamFactroy.init(true);
			xs.alias("xml", RespVideoMsg.class);
			xs.aliasField("Video", Video.class, "video");
			result = xs.toXML(video);
		}
		return result;
	}

	/**
	 * 语音消息
	 * 
	 * @param voice
	 * @return
	 */
	public static String voiceMsgToXml(RespVoiceMsg voice) {
		String result = "";
		if (voice != null) {
			XStream xs = XStreamFactroy.init(true);
			xs.alias("xml", RespVoiceMsg.class);
			xs.aliasField("Voice", Voice.class, "voice");
			result = xs.toXML(voice);
		}
		return result;
	}

	/**
	 * 支付参数
	 * @param params
	 * @return
	 */
	public static String abstractPayToXml(AbstractPayParams params) {
		String sign = SignatureUtil.createSign(params, null, null);
		params.setSign(sign);
		return XmlUtil.toSplitXml(params);
	}
}