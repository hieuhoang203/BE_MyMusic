package com.example.music.mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPassWord(String to, String subject, String passWord, String name) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);

            String htmlText = "<html lang=\"en\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <style>\n" +
                    "        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap');\n" +
                    "\n" +
                    "        * {\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            box-sizing: border-box;\n" +
                    "            font-family: 'Roboto', sans-serif;\n" +
                    "            outline: 0;\n" +
                    "            appearance: none;\n" +
                    "            border: 0;\n" +
                    "            text-decoration: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        .container {\n" +
                    "            height: 100vh;\n" +
                    "            padding: 10px;\n" +
                    "            background-color: #181a1e;\n" +
                    "            color: #fff;\n" +
                    "            font-size: 20px;\n" +
                    "        }\n" +
                    "\n" +
                    "        b {\n" +
                    "            color: #1B9C85;\n" +
                    "        }\n" +
                    "\n" +
                    "        .container div {\n" +
                    "            margin-left: 600px;\n" +
                    "            height: 80px;\n" +
                    "            width: 80px;\n" +
                    "            margin-bottom: 10px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .container div img {\n" +
                    "            height: 100%;\n" +
                    "            width: 100%;\n" +
                    "            object-fit: cover;\n" +
                    "            border-radius: 50%;\n" +
                    "        }\n" +
                    "\n" +
                    "        p {\n" +
                    "            margin-top: 10px;\n" +
                    "            margin-left: 350px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .container p a img {\n" +
                    "            margin-top: 10px;\n" +
                    "            width: 50px;\n" +
                    "            height: 50px;\n" +
                    "            object-fit: cover;\n" +
                    "            border-radius: 50%;\n" +
                    "        }\n" +
                    "\n" +
                    "        .container p a img:hover {\n" +
                    "            cursor: pointer;\n" +
                    "        }\n" +
                    "\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"\">\n" +
                    "            <img src=\"https://res.cloudinary.com/hieuhv203/image/upload/v1714844039/assetHtml/lejvnpsmmpclqjlcnfpz.png\"\n" +
                    "                alt=\"\">\n" +
                    "        </div>\n" +
                    "        <p>\n" +
                    "            Xin chào <b>"+ name +"</b>!\n" +
                    "        </p>\n" +
                    "        <p>\n" +
                    "            Tài khoản Sublime của bạn đã được tạo với mật khẩu là: <b><i>" + passWord +"</i></b>\n" +
                    "        </p>\n" +
                    "        <p>\n" +
                    "            Chúc bạn có những phút giây thư giãn và giải trí với <b>Sublime</b>!\n" +
                    "        </p>\n" +
                    "        <p>\n" +
                    "            Mọi chi tiết hỗ trợ xin liên hệ:\n" +
                    "            <br>\n" +
                    "            <a href=\"\">\n" +
                    "                <img src=\"https://icones.pro/wp-content/uploads/2021/02/instagram-logo-icone4.png\" alt=\"\">\n" +
                    "            </a>\n" +
                    "        </p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCodeChangePassWord(String to, String subject, String htmlText) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
