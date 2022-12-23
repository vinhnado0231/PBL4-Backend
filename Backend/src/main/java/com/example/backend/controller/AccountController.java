package com.example.backend.controller;

import com.example.backend.model.Account;
import com.example.backend.service.IAccountService;
import com.example.backend.ultil.ForgotPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.example.backend.ultil.ScheduledTasks.tokenForgotPasswordSet;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgot-password")
    public ResponseEntity<Object> processForgotPasswordForm(@RequestParam String email) {
        String message = "";
        try {
            Account account = accountService.findAccountByEmail(email);
            if (account == null) {
                message = "Email not valid";
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
            accountService.updateToken(account);
            String resetPasswordLink = "http://localhost:3000/home/change-password/" + account.getToken();
            sendEmailForgotPassword(email, resetPasswordLink);
            message = "We have sent a link to change the password. Please open email to check";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (UnsupportedEncodingException | MessagingException exception) {
            message = "Error sending mail";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            message = "Exception Error";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> processResetPassword(@RequestBody String token,
                                                       @RequestBody String password) {
        String message = "OK";
        Account account = accountService.findAccountByToken(token);
        if (account == null) {
            message = "Token invaid";
        } else {
            for (ForgotPasswordToken tokenCheck : tokenForgotPasswordSet) {
                message = "Token is expired or invalid please try again";
                if (tokenCheck.getToken().equals(token)) {
                    int diff = LocalDateTime.now().compareTo(tokenCheck.getTime());
                    if (diff >= 0) {
                        tokenForgotPasswordSet.remove(tokenCheck);
                        break;
                    } else {
                        accountService.saveForgotPassword(account, password);
                        message = "Change password successfully";
                        tokenForgotPasswordSet.remove(tokenCheck);
                        break;
                    }
                }
            }
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private void sendEmailForgotPassword(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");
        helper.setFrom("EME@BestTeamCoder.com", "Lô đổi mật khẩu này ");
        helper.setTo(email);
        String subject = "Chat website - Forgot Password:";
        String content = "<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "  <!-- NAME: 1 COLUMN -->\n" +
                "  <!--[if gte mso 15]>\n" +
                "      <xml>\n" +
                "        <o:OfficeDocumentSettings>\n" +
                "          <o:AllowPNG/>\n" +
                "          <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "        </o:OfficeDocumentSettings>\n" +
                "      </xml>\n" +
                "    <![endif]-->\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <title>Reset Your Password Here</title>\n" +
                "  <!--[if !mso]>\n" +
                "      <!-- -->\n" +
                "  <link href='https://fonts.googleapis.com/css?family=Asap:400,400italic,700,700italic' rel='stylesheet' type='text/css'>\n" +
                "  <!--<![endif]-->\n" +
                "  <style type=\"text/css\">\n" +
                "    @media only screen and (min-width:768px){\n" +
                "          .templateContainer{\n" +
                "              width:600px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          body,table,td,p,a,li,blockquote{\n" +
                "              -webkit-text-size-adjust:none !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          body{\n" +
                "              width:100% !important;\n" +
                "              min-width:100% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          #bodyCell{\n" +
                "              padding-top:10px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnImage{\n" +
                "              width:100% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "         \n" +
                "   .mcnCaptionTopContent,.mcnCaptionBottomContent,.mcnTextContentContainer,.mcnBoxedTextContentContainer,.mcnImageGroupContentContainer,.mcnCaptionLeftTextContentContainer,.mcnCaptionRightTextContentContainer,.mcnCaptionLeftImageContentContainer,.mcnCaptionRightImageContentContainer,.mcnImageCardLeftTextContentContainer,.mcnImageCardRightTextContentContainer{\n" +
                "              max-width:100% !important;\n" +
                "              width:100% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnBoxedTextContentContainer{\n" +
                "              min-width:100% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnImageGroupContent{\n" +
                "              padding:9px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnCaptionLeftContentOuter\n" +
                "   .mcnTextContent,.mcnCaptionRightContentOuter .mcnTextContent{\n" +
                "              padding-top:9px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnImageCardTopImageContent,.mcnCaptionBlockInner\n" +
                "   .mcnCaptionTopContent:last-child .mcnTextContent{\n" +
                "              padding-top:18px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnImageCardBottomImageContent{\n" +
                "              padding-bottom:9px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnImageGroupBlockInner{\n" +
                "              padding-top:0 !important;\n" +
                "              padding-bottom:0 !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnImageGroupBlockOuter{\n" +
                "              padding-top:9px !important;\n" +
                "              padding-bottom:9px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnTextContent,.mcnBoxedTextContentColumn{\n" +
                "              padding-right:18px !important;\n" +
                "              padding-left:18px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcnImageCardLeftImageContent,.mcnImageCardRightImageContent{\n" +
                "              padding-right:18px !important;\n" +
                "              padding-bottom:0 !important;\n" +
                "              padding-left:18px !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "          .mcpreview-image-uploader{\n" +
                "              display:none !important;\n" +
                "              width:100% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Heading 1\n" +
                "      @tip Make the first-level headings larger in size for better readability\n" +
                "   on small screens.\n" +
                "      */\n" +
                "          h1{\n" +
                "              /*@editable*/font-size:20px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Heading 2\n" +
                "      @tip Make the second-level headings larger in size for better\n" +
                "   readability on small screens.\n" +
                "      */\n" +
                "          h2{\n" +
                "              /*@editable*/font-size:20px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Heading 3\n" +
                "      @tip Make the third-level headings larger in size for better readability\n" +
                "   on small screens.\n" +
                "      */\n" +
                "          h3{\n" +
                "              /*@editable*/font-size:18px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Heading 4\n" +
                "      @tip Make the fourth-level headings larger in size for better\n" +
                "   readability on small screens.\n" +
                "      */\n" +
                "          h4{\n" +
                "              /*@editable*/font-size:16px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Boxed Text\n" +
                "      @tip Make the boxed text larger in size for better readability on small\n" +
                "   screens. We recommend a font size of at least 16px.\n" +
                "      */\n" +
                "          .mcnBoxedTextContentContainer\n" +
                "   .mcnTextContent,.mcnBoxedTextContentContainer .mcnTextContent p{\n" +
                "              /*@editable*/font-size:16px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Preheader Visibility\n" +
                "      @tip Set the visibility of the email's preheader on small screens. You\n" +
                "   can hide it to save space.\n" +
                "      */\n" +
                "          #templatePreheader{\n" +
                "              /*@editable*/display:block !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Preheader Text\n" +
                "      @tip Make the preheader text larger in size for better readability on\n" +
                "   small screens.\n" +
                "      */\n" +
                "          #templatePreheader .mcnTextContent,#templatePreheader\n" +
                "   .mcnTextContent p{\n" +
                "              /*@editable*/font-size:12px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Header Text\n" +
                "      @tip Make the header text larger in size for better readability on small\n" +
                "   screens.\n" +
                "      */\n" +
                "          #templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{\n" +
                "              /*@editable*/font-size:16px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Body Text\n" +
                "      @tip Make the body text larger in size for better readability on small\n" +
                "   screens. We recommend a font size of at least 16px.\n" +
                "      */\n" +
                "          #templateBody .mcnTextContent,#templateBody .mcnTextContent p{\n" +
                "              /*@editable*/font-size:16px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }   @media only screen and (max-width: 480px){\n" +
                "      /*\n" +
                "      @tab Mobile Styles\n" +
                "      @section Footer Text\n" +
                "      @tip Make the footer content text larger in size for better readability\n" +
                "   on small screens.\n" +
                "      */\n" +
                "          #templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{\n" +
                "              /*@editable*/font-size:12px !important;\n" +
                "              /*@editable*/line-height:150% !important;\n" +
                "          }\n" +
                "  \n" +
                "  }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"-ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " background-color: #fed149; height: 100%; margin: 0; padding: 0; width: 100%\">\n" +
                "  <center>\n" +
                "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" id=\"bodyTable\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; background-color: #fed149; height: 100%; margin: 0; padding: 0; width:\n" +
                " 100%\" width=\"100%\">\n" +
                "      <tr>\n" +
                "        <td align=\"center\" id=\"bodyCell\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; border-top: 0;\n" +
                " height: 100%; margin: 0; padding: 0; width: 100%\" valign=\"top\">\n" +
                "          <!-- BEGIN TEMPLATE // -->\n" +
                "          <!--[if gte mso 9]>\n" +
                "              <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:600px;\">\n" +
                "                <tr>\n" +
                "                  <td align=\"center\" valign=\"top\" width=\"600\" style=\"width:600px;\">\n" +
                "                  <![endif]-->\n" +
                "          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"templateContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; max-width:\n" +
                " 600px; border: 0\" width=\"100%\">\n" +
                "            <tr>\n" +
                "              <td id=\"templatePreheader\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #fed149;\n" +
                " border-top: 0; border-bottom: 0; padding-top: 16px; padding-bottom: 8px\" valign=\"top\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                  <tbody class=\"mcnTextBlockOuter\">\n" +
                "                    <tr>\n" +
                "                      <td class=\"mcnTextBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%\" valign=\"top\">\n" +
                "                        <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td id=\"templateHeader\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #f7f7ff;\n" +
                " border-top: 0; border-bottom: 0; padding-top: 16px; padding-bottom: 0\" valign=\"top\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnImageBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                  <tbody class=\"mcnImageBlockOuter\">\n" +
                "                    <tr>\n" +
                "                      <td class=\"mcnImageBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding:0px\" valign=\"top\">\n" +
                "                        <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnImageContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td class=\"mcnImageContent\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-right: 0px;\n" +
                " padding-left: 0px; padding-top: 0; padding-bottom: 0; text-align:center;\" valign=\"top\">\n" +
                "                                <a class=\"\" href=\"http://localhost:4200/\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color:\n" +
                " #f57153; font-weight: normal; text-decoration: none\" target=\"_blank\" title=\"\">\n" +
                "                                  <a class=\"\" href=\"http://localhost:4200/\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color:\n" +
                " #f57153; font-weight: normal; text-decoration: none\" target=\"_blank\" title=\"\">\n" +
                "                                    <img align=\"center\" alt=\"Forgot your password?\" class=\"mcnImage\" src=\"https://static.lingoapp.com/assets/images/email/il-password-reset@2x.png\" style=\"-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                " text-decoration: none; vertical-align: bottom; max-width:1200px; padding-bottom:\n" +
                " 0; display: inline !important; vertical-align: bottom;\" width=\"600\"></img>\n" +
                "                                  </a>\n" +
                "                                </a>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td id=\"templateBody\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #f7f7ff;\n" +
                " border-top: 0; border-bottom: 0; padding-top: 0; padding-bottom: 0\" valign=\"top\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                  <tbody class=\"mcnTextBlockOuter\">\n" +
                "                    <tr>\n" +
                "                      <td class=\"mcnTextBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%\" valign=\"top\">\n" +
                "                        <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td class=\"mcnTextContent\" style='mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; word-break: break-word;\n" +
                " color: #2a2a2a; font-family: \"Asap\", Helvetica, sans-serif; font-size: 16px;\n" +
                " line-height: 150%; text-align: center; padding-top:9px; padding-right: 18px;\n" +
                " padding-bottom: 9px; padding-left: 18px;' valign=\"top\">\n" +
                "\n" +
                "                                <h1 class=\"null\" style='color: #2a2a2a; font-family: \"Asap\", Helvetica,\n" +
                " sans-serif; font-size: 32px; font-style: normal; font-weight: bold; line-height:\n" +
                " 125%; letter-spacing: 2px; text-align: center; display: block; margin: 0;\n" +
                " padding: 0'><span style=\"text-transform:uppercase\">Forgot</span></h1>\n" +
                "\n" +
                "\n" +
                "                                <h2 class=\"null\" style='color: #2a2a2a; font-family: \"Asap\", Helvetica,\n" +
                " sans-serif; font-size: 24px; font-style: normal; font-weight: bold; line-height:\n" +
                " 125%; letter-spacing: 1px; text-align: center; display: block; margin: 0;\n" +
                " padding: 0'><span style=\"text-transform:uppercase\">your password?</span></h2>\n" +
                "\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace:\n" +
                " 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                  <tbody class=\"mcnTextBlockOuter\">\n" +
                "                    <tr>\n" +
                "                      <td class=\"mcnTextBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%\" valign=\"top\">\n" +
                "                        <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td class=\"mcnTextContent\" style='mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; word-break: break-word;\n" +
                " color: #2a2a2a; font-family: \"Asap\", Helvetica, sans-serif; font-size: 16px;\n" +
                " line-height: 150%; text-align: center; padding-top:9px; padding-right: 18px;\n" +
                " padding-bottom: 9px; padding-left: 18px;' valign=\"top\">Not to worry, we got you! Let’s get you a new password. But this link only lasts for 10 minutes.\n" +
                "                                <br></br>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnButtonBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " min-width:100%;\" width=\"100%\">\n" +
                "                  <tbody class=\"mcnButtonBlockOuter\">\n" +
                "                    <tr>\n" +
                "                      <td align=\"center\" class=\"mcnButtonBlockInner\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " padding-top:18px; padding-right:18px; padding-bottom:18px; padding-left:18px;\" valign=\"top\">\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnButtonBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                          <tbody class=\"mcnButtonBlockOuter\">\n" +
                "                            <tr>\n" +
                "                              <td align=\"center\" class=\"mcnButtonBlockInner\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " padding-top:0; padding-right:18px; padding-bottom:18px; padding-left:18px;\" valign=\"top\">\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnButtonContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " border-collapse: separate !important;border-radius: 48px;background-color:\n" +
                " #F57153;\">\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td align=\"center\" class=\"mcnButtonContent\" style=\"mso-line-height-rule:\n" +
                " exactly; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\n" +
                " font-family: 'Asap', Helvetica, sans-serif; font-size: 16px; padding-top:24px;\n" +
                " padding-right:48px; padding-bottom:24px; padding-left:48px;\" valign=\"middle\">\n" +
                "                                        <a class=\"mcnButton \" href=" + resetPasswordLink + " style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; display: block; color: #f57153;\n" +
                " font-weight: normal; text-decoration: none; font-weight: normal;letter-spacing:\n" +
                " 1px;line-height: 100%;text-align: center;text-decoration: none;color:\n" +
                " #FFFFFF; text-transform:uppercase;\" target=\"_blank\" title=\"Review your change password site\n" +
                " invitation\">Reset password</a>\n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnImageBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                  <tbody class=\"mcnImageBlockOuter\">\n" +
                "                    <tr>\n" +
                "                      <td class=\"mcnImageBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding:0px\" valign=\"top\">\n" +
                "                        <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnImageContentContainer\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; min-width:100%;\" width=\"100%\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td class=\"mcnImageContent\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-right: 0px;\n" +
                " padding-left: 0px; padding-top: 0; padding-bottom: 0; text-align:center;\" valign=\"top\"></td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td id=\"templateFooter\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; background-color: #fed149;\n" +
                " border-top: 0; border-bottom: 0; padding-top: 8px; padding-bottom: 80px\" valign=\"top\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mcnTextBlock\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                  <tbody class=\"mcnTextBlockOuter\">\n" +
                "                    <tr>\n" +
                "                      <td class=\"mcnTextBlockInner\" style=\"mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%\" valign=\"top\">\n" +
                "                        <table align=\"center\" bgcolor=\"#F7F7FF\" border=\"0\" cellpadding=\"32\" cellspacing=\"0\" class=\"card\" style=\"border-collapse: collapse; mso-table-lspace: 0;\n" +
                " mso-table-rspace: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust:\n" +
                " 100%; background:#F7F7FF; margin:auto; text-align:left; max-width:600px;\n" +
                " font-family: 'Asap', Helvetica, sans-serif;\" text-align=\"left\" width=\"100%\">\n" +
                "                          <tr>\n" +
                "                            <td style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%\">\n" +
                "\n" +
                "                              <h3 style='color: #2a2a2a; font-family: \"Asap\", Helvetica, sans-serif;\n" +
                " font-size: 20px; font-style: normal; font-weight: normal; line-height: 125%;\n" +
                " letter-spacing: normal; text-align: center; display: block; margin: 0; padding:\n" +
                " 0; text-align: left; width: 100%; font-size: 16px; font-weight: bold; '>What\n" +
                " is EME webchat?</h3>\n" +
                "\n" +
                "                              <p style='margin: 10px 0; padding: 0; mso-line-height-rule: exactly;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; color: #2a2a2a;\n" +
                " font-family: \"Asap\", Helvetica, sans-serif; font-size: 12px; line-height: 150%;\n" +
                " text-align: left; text-align: left; font-size: 14px; '>EME Messenger is a website that enables chat, chat voice and communications between the social media site's web-based messaging and smartphones.\n" +
                "                              </p>\n" +
                "                              <div style=\"padding-bottom: 18px;\">\n" +
                "                                <a href=\"http://localhost:3000/\" style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration: none;\n" +
                " font-size: 14px; color:#F57153; text-decoration:none;\" target=\"_blank\" title=\"Learn more about EME webchat\">Learn More ❯</a>\n" +
                "                              </div>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; mso-table-lspace: 0; mso-table-rspace: 0;\n" +
                " -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; min-width:100%;\" width=\"100%\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                            </tr>\n" +
                "                            <tbody></tbody>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"12\" style=\"border-collapse:\n" +
                " collapse; mso-table-lspace: 0; mso-table-rspace: 0; -ms-text-size-adjust:\n" +
                " 100%; -webkit-text-size-adjust: 100%; \">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%\">\n" +
                "                                <a href=\"https://twitter.com\" style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration: none\" target=\"_blank\">\n" +
                "                                  <img alt=\"twitter\" height=\"32\" src=\"https://static.lingoapp.com/assets/images/email/twitter-ic-32x32-email@2x.png\" style=\"-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none; text-decoration:\n" +
                " none\" width=\"32\" />\n" +
                "                                </a>\n" +
                "                              </td>\n" +
                "                              <td style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%\">\n" +
                "                                <a href=\"https://www.instagram.com/\" style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration:\n" +
                " none\" target=\"_blank\">\n" +
                "                                  <img alt=\"Instagram\" height=\"32\" src=\"https://static.lingoapp.com/assets/images/email/instagram-ic-32x32-email@2x.png\" style=\"-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                " text-decoration: none\" width=\"32\" />\n" +
                "                                </a>\n" +
                "                              </td>\n" +
                "                              <td style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%\">\n" +
                "                                <a href=\"https://medium.com\" style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration: none\" target=\"_blank\">\n" +
                "                                  <img alt=\"medium\" height=\"32\" src=\"https://static.lingoapp.com/assets/images/email/medium-ic-32x32-email@2x.png\" style=\"-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none; text-decoration: none\" width=\"32\" />\n" +
                "                                </a>\n" +
                "                              </td>\n" +
                "                              <td style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%\">\n" +
                "                                <a href=\"https://www.facebook.com\" style=\"mso-line-height-rule: exactly; -ms-text-size-adjust: 100%;\n" +
                " -webkit-text-size-adjust: 100%; color: #f57153; font-weight: normal; text-decoration: none\" target=\"_blank\">\n" +
                "                                  <img alt=\"facebook\" height=\"32\" src=\"https://static.lingoapp.com/assets/images/email/facebook-ic-32x32-email@2x.png\" style=\"-ms-interpolation-mode: bicubic; border: 0; height: auto; outline: none;\n" +
                " text-decoration: none\" width=\"32\" />\n" +
                "                                </a>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "          <!--[if gte mso 9]>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            <![endif]-->\n" +
                "          <!-- // END TEMPLATE -->\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </center>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        System.out.println("Done send email forgot password to " + email);
    }


}
