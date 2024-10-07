package com.tenco.blog_v1.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    // DI
    // @Autowired
    private final BoardNativeRepository boardNativeRepository;

//    public BoardController(BoardNativeRepository boardNativeRepository) {
//        this.boardNativeRepository = boardNativeRepository;
//    }

    @GetMapping("/")
    public String index(Model model) {

        List<Board> boardList = boardNativeRepository.findAll();
        model.addAttribute("boardList", boardList);
        return "index";
    }

    // 주소설계 http://localhost:8080/board/save-form
    // 게시글 작성 화면
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

    @PostMapping("/board/save")
    public String save(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content) {

        log.warn(title, content);

        boardNativeRepository.save(title, content);
        return "redirect:/";
    }

    // 주소설계 http://localhost:8080/board/10
    // 특정 게시글 요청 화면
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);
        return "board/detail";
    }

    // 주소설계 http://localhost:8080/board/10/delete
    // form 태그는 GET, POST 방식만 지원
    @PostMapping("/board/{id}/detail")
    public String delete(@PathVariable(name = "id") Integer id) {
        boardNativeRepository.deleteById(id);
        return "redirect:/";
    }

    // 게시글 수정 화면 요청
    // board/id/update-form
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);
        return "board/update-form";
    }

    // 게시글 수정
    // board/id/update
    @PostMapping("/borad/{id}/update")
    public String update(@PathVariable(name = "id") Integer id, @RequestParam(name = "title") String title, @RequestParam(name = "content") String content) {

        boardNativeRepository.updateById(id, title, content);

        return "redirect:/board/" + id;
    }

}
