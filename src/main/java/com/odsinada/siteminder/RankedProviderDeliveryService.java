package com.odsinada.siteminder;

import java.util.Iterator;
import java.util.List;

public class RankedProviderDeliveryService implements MailDeliveryService {

    private final List<EmailProvider> providers;

    public RankedProviderDeliveryService(List<EmailProvider> providers) {
        this.providers = providers;
    }

    @Override
    public void send(EmailDetails email) throws MailDeliveryException {

        boolean isPendingSend = true;
        Iterator<EmailProvider> providerIter = providers.iterator();
        while(isPendingSend && providerIter.hasNext()){
            isPendingSend = !providerIter.next().send(email);
        }

        if (isPendingSend) {
            throw new MailDeliveryException("Email has not been sent by any of the providers.");
        }

    }
}
