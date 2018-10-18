package de.hhu.bsinfo.restTerminal.customization;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class SimpleBannerProvider extends BannerProvider {
//
//    public String getBanner() {
//        StringBuffer buf = new StringBuffer();
//        buf.append("=======================================")
//                .append(OsUtils.LINE_SEPARATOR);
//        buf.append("*          Baeldung Shell             *")
//                .append(OsUtils.LINE_SEPARATOR);
//        buf.append("=======================================")
//                .append(OsUtils.LINE_SEPARATOR);
//        buf.append("Version:")
//                .append(this.getVersion());
//        return buf.toString();
//    }
//
//    public String getVersion() {
//        return "1.0.1";
//    }
//
//    public String getWelcomeMessage() {
//        return "Welcome to Baeldung CLI";
//    }
//
//    public String getProviderName() {
//        return "Baeldung Banner";
//    }
//}
