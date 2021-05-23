package com.example.demo;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.saml2.provider.service.authentication.OpenSamlAuthenticationProvider;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GrantedAuthorityPostProcessor implements ObjectPostProcessor<AuthenticationProvider> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrantedAuthorityPostProcessor.class);

    public <O extends AuthenticationProvider> O postProcess(O object) {
        if (object instanceof OpenSamlAuthenticationProvider) {
            ((OpenSamlAuthenticationProvider) object).setAuthoritiesExtractor(authoritiesExtractor);
        }
        return object;
    }

    public List<GrantedAuthority> converter(Assertion assertion) {
        LOGGER.info("Converting assertion finding groups");
        try {
            final List<Attribute> attributes = assertion.getAttributeStatements().get(0).getAttributes();
            final List<Attribute> groups = attributes.stream().filter(x -> x.getName().equals("groups")).collect(Collectors.toList());
            return groups.get(0).getAttributeValues().stream().map(this::newAuthority).collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.error("Could not find group for user in assertion {}", assertion);
            throw ex;
        }
    }

    private Converter<Assertion, Collection<? extends GrantedAuthority>> authoritiesExtractor =
            (a -> converter(a));

    private SimpleGrantedAuthority newAuthority(XMLObject y) {
        final String value = ((XSStringImpl) y).getValue();
        return new SimpleGrantedAuthority(value);
    }
}