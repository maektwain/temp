package org.mifosplatform.infrastructure.core.service;

import org.mifosplatform.infrastructure.core.domain.EmailDetail;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SendClientMail implements PlatformEmailService {
	
	
	@Override
    public void sendToClientAccount(final EmailDetail emailDetail, final String unencodedPassword) {
        final Email email = new SimpleEmail();

        final String authuserName = "dev-ops@theupscale.in";

        final String authuser = "dev-ops@theupscale.in";
        final String authpwd = "upscale!23SDF";

        // Very Important, Don't use email.setAuthentication()
        email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
        email.setDebug(false); // true if you want to debug
        email.setHostName("smtp.gmail.com");
        try {
            email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
            email.setFrom(authuser, authuserName);

            final StringBuilder subjectBuilder = new StringBuilder().append("Upscale Financial: ").append(emailDetail.getContactName())
                    .append(" user account creation.");

            email.setSubject(subjectBuilder.toString());

            final String sendToEmail = emailDetail.getAddress();

            final StringBuilder messageBuilder = new StringBuilder().append("You are receiving this email as your email account: ")
                    .append(sendToEmail).append(" has being used to create a Client account for Decimus Financial [")
                    .append(emailDetail.getOrganisationName()).append("] on Upscale Financial.")
                    .append("You need to share the following OTP: username: ").append(emailDetail.getUsername())
                    .append(" password: ").append(unencodedPassword);

            email.setMsg(messageBuilder.toString());

            email.addTo(sendToEmail, emailDetail.getContactName());
            email.send();
        } catch (final EmailException e) {
            throw new PlatformEmailSendException(e);
        }
    }

	@Override
	public void sendToUserAccount(EmailDetail emailDetail,
			String unencodedPassword) {
		// TODO Auto-generated method stub
		
	}
	

}
