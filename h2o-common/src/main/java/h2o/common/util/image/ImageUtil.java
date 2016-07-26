package h2o.common.util.image;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.image.gif.AnimatedGifEncoder;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;

public class ImageUtil {

	private ImageUtil() {}

	public static void convert(String source, String dest) {
		convert(source, dest, 0, 0, 75);
	}

	public static void convert(String source, String dest, int quality) {
		convert(source, dest, 0, 0, quality);
	}

	public static void convert(String source, String dest, int w, int h, int quality) {
		convert(source, dest, w, h, quality, true);
	}

	public static void convert(String source, String dest, int w, int h, int quality, boolean ismem) {
		String temp = dest + "_temp.jpg";
		convert(toJPG(source, temp, quality, ismem), dest, w, h);
		if (!ismem) {
			File tf = new File(temp);
			if (tf.exists()) {
				tf.delete();
			}
		}
	}

	public static void convert(BufferedImage src, String dest, int w, int h) {

		try {

			BufferedImage tag = src;
			if (w != 0) {
				tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(src, 0, 0, w, h, null);
			}

			if (dest.toLowerCase().trim().endsWith("gif")) {
				AnimatedGifEncoder e = new AnimatedGifEncoder();
				e.start(dest);
				e.addFrame(tag);
				e.finish();
			} else if (dest.toLowerCase().trim().endsWith("tif") || dest.toLowerCase().trim().endsWith("tiff")) {
				toTIF(tag, dest);
			} else {
				JimiWriter writer = Jimi.createJimiWriter(dest);
				writer.setSource(tag);
				writer.putImage(dest);
			}

		} catch (Exception e) {
			Tools.log.debug("convert", e);
			throw ExceptionUtil.toRuntimeException(e);
		}

	}

	public static BufferedImage toJPG(String source, String temp, int quality, boolean ismem) {

		try {
			JPGOptions options = new JPGOptions();
			options.setQuality(quality);
			ImageProducer image = Jimi.getImageProducer(source);
			JimiWriter writer = Jimi.createJimiWriter(temp);
			writer.setSource(image);
			writer.setOptions(options);

			if (ismem) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				writer.putImage(bos);
				InputStream input = new ByteArrayInputStream(bos.toByteArray());
				return javax.imageio.ImageIO.read(input);
			} else {
				writer.putImage(temp);
				return javax.imageio.ImageIO.read(new File(temp));
			}

		} catch (Exception je) {
			Tools.log.debug("toJPG", je);
			throw ExceptionUtil.toRuntimeException(je);
		}
	}

	private static void toTIF(Image img, String dest) throws JimiException {
		if (!dest.toLowerCase().trim().endsWith("tif")) {
			dest += ".tif";
			System.out.println("Overriding to TIF, output file: " + dest);
		}
		dest = dest.substring(0, dest.lastIndexOf(".")) + ".jpg";
		JimiWriter writer = Jimi.createJimiWriter(dest);
		writer.setSource(img);
		dest = dest.substring(0, dest.lastIndexOf(".")) + ".tif";
		writer.putImage(dest);
	}

}
