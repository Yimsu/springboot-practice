package app.messages;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/messages")
public class MessageController {
  private MessageService messageService;

  @GetMapping("/welcome")
  public String welcome(Model model) {
    model.addAttribute("message", "Hello, Welcome to Spring Boot!");
    return "welcome";
  }


  @PostMapping("/message")
 // @ResponseBody
  // ResponseEntity은 응답상태, 본문과 헤더를 설정할 수 있게 허용한다.
  public ResponseEntity<Message> saveMessage(@RequestBody MessageData data) {
    Message saved = messageService.save(data.getText());
    if (saved == null) {
      return ResponseEntity.status(500).build();
    }
    return ResponseEntity.ok(saved);
  }
}
