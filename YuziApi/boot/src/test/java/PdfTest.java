import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author ShenZhaoYu
 * @date 2024/9/1
 * @描述
 */
public class PdfTest {
    String pdfFilePath = "D:\\Zhaoyu\\Learn\\python_spider\\Model\\output_xuanzhuan.pdf"; // PDF文件路径
    @Test
    public void testGetPdf() {
        try {
            // 加载PDF文档
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            // 创建PDFTextStripper来提取文本
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // 获取PDF中的所有文本
            String text = pdfStripper.getText(document);

            // 打印文本内容
            System.out.println(text);

            // 关闭文档
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
