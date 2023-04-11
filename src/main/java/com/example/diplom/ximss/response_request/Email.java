package com.example.diplom.ximss.response_request;

import com.example.diplom.ximss.response.Date;
import com.example.diplom.ximss.response.Mime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "EMail")
public class Email {

    @JacksonXmlProperty(localName = "Return-Path")
    private String returnPath;

    @JacksonXmlProperty(localName = "Received")
    private String received;

    @JacksonXmlProperty(localName = "From")
    private String from;

    @JacksonXmlProperty(localName = "Subject")
    private String subject;

    @JacksonXmlProperty(localName = "To")
    private String to;

    @JacksonXmlProperty(localName = "Cc")
    private String cc;

    @JacksonXmlProperty(localName = "X-Mailer")
    private String xMailer;
    @JacksonXmlProperty(localName = "Date")
    private Date date;

    @JacksonXmlProperty(localName = "Message-ID")
    private String messageId;

    @JacksonXmlProperty(localName = "MIME")
    private Mime mime;

}

