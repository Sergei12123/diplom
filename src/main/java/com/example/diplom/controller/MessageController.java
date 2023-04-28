package com.example.diplom.controller;

import com.example.diplom.dto.MessageDTO;
import com.example.diplom.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/new-message")
    private String newMessagePage(Model model) {
        if (model.getAttribute("message") == null)
            model.addAttribute("message", MessageDTO.builder().build());
        return "new-message";
    }

    @PostMapping("/sendMessage")
    private String sendMessage(@ModelAttribute("message") MessageDTO messageDTO, RedirectAttributes redirectAttributes) {
        if (messageDTO.isReply()) {
            messageService.replyFromMessage(messageDTO);
        } else if (messageDTO.isForward()) {
            messageService.forwardMessage(messageDTO);
        } else {
            messageService.sendMessage(messageDTO);
        }
        redirectAttributes.addAttribute("messageSent", true);
        redirectAttributes.addAttribute("userName", messageDTO.getUserLogin());
        return "redirect:/hello";
    }

    @PostMapping("/deleteMessages")
    private String deleteMessages(@RequestParam(value = "selectedMessages", required = false) Long[] selectedMessages) {
        if (selectedMessages == null) return "redirect:/hello";
        messageService.deleteMessages(selectedMessages);
        return "redirect:/hello" + (selectedMessages.length == 1 ? "?messageDeleted" : "?messagesDeleted");
    }

    @RequestMapping("/hello")
    private String helloPage(Model model) {
        model.addAttribute("messages", messageService.getInboxMessages());
        return "hello";
    }

    @PostMapping(value = "/replyToMessage/{uid}")
    public String replyToMessage(@PathVariable("uid") Long uid, RedirectAttributes redirectAttributes) {
        final MessageDTO tempDto = messageService.getMessageByUid(uid);

        redirectAttributes.addFlashAttribute("message",
            MessageDTO.builder()
                .uid(uid)
                .reply(true)
                .replyText(tempDto.getText())
                .userLogin(tempDto.getUserLogin())
                .replyFrom(tempDto.getUserLogin())
                .replyTitle(tempDto.getReplyTitle())
                .title(tempDto.getTitle())
                .build());
        return "redirect:/new-message";
    }

    @PostMapping(value = "/forwardToMessage/{uid}")
    public String forwardToMessage(@PathVariable("uid") Long uid, RedirectAttributes redirectAttributes) {
        final MessageDTO tempDto = messageService.getMessageByUid(uid);

        redirectAttributes.addFlashAttribute("message",
            MessageDTO.builder()
                .uid(uid)
                .forward(true)
                .replyText(tempDto.getText())
                .replyTitle(tempDto.getReplyTitle())
                .replyFrom(tempDto.getUserLogin())
                .title(tempDto.getTitle())
                .build());
        return "redirect:/new-message";
    }

}
