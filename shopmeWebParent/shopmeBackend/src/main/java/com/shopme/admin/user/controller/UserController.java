package com.shopme.admin.user.controller;

import com.shopme.admin.user.service.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import com.shopme.common.utils.FileUploadUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@SessionAttributes
public class UserController {

    @Autowired
    UserService service;



    @GetMapping("/users")
    public String users(Model model, HttpSession session){
        if(Objects.isNull(session.getAttribute("pageNumber")))
            session.setAttribute("pageNumber", 1);
        if(Objects.isNull(session.getAttribute("sortColumn")))
            session.setAttribute("sortColumn", "email");
        if(Objects.isNull(session.getAttribute("sortOrder")))
            session.setAttribute("sortOrder", "asc");
            session.setAttribute("keyword", null);

        return usersByPage(Integer.valueOf(session.getAttribute("pageNumber").toString()),
                             session.getAttribute("sortColumn").toString(),
                             session.getAttribute("sortOrder").toString(),
                              Objects.isNull(session.getAttribute("keyword")) ? null : session.getAttribute("keyword").toString(),
                              model, session );
    }

    @GetMapping("/users/page/{pageNum}")
    public String usersByPage(@PathVariable int pageNum,
                              @RequestParam("sortColumn") String sortColumn,
                              @RequestParam("sortOrder") String sortOrder,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              Model model,
                              HttpSession session){
        if(Objects.isNull(keyword))
        System.out.println("keyword :: is Null ");
                session.setAttribute("pageNumber", pageNum);
                session.setAttribute("sortColumn", sortColumn);
                session.setAttribute("sortOrder", sortOrder);
                session.setAttribute("keyword", keyword);

        Page<User> page = service.findAllUsersByPage(pageNum,sortColumn, sortOrder, keyword);

        int itemsOnPage = service.PAGE_SIZE;
        long TotalElements = page.getTotalElements();
        int TotalPages = page.getTotalPages();

        System.out.println("itemsOnPage ::"+itemsOnPage);
        System.out.println("TotalElements ::"+TotalElements);
        System.out.println("TotalPages ::"+TotalPages);

        model.addAttribute("usersList", page.getContent());
        model.addAttribute("itemsOnPage", itemsOnPage);
        model.addAttribute("totalElements", TotalElements );
        model.addAttribute("totalPages", TotalPages);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("keyword", keyword);
        StringBuilder paginationMessage = new StringBuilder();
        if(TotalElements==0) paginationMessage.append("No Users found");
        else if(TotalElements<= itemsOnPage) paginationMessage.append("");
        else paginationMessage.append(String.format("Showing  Users # %d to %d out of %d", ((itemsOnPage*(pageNum-1))+1), (itemsOnPage*pageNum < TotalElements)? itemsOnPage*pageNum : TotalElements , TotalElements));
        model.addAttribute("paginationMessage", paginationMessage.toString());

        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        User user = new User();
        List<Role> roles= service.findAllRoles();
        model.addAttribute("rolesList", roles);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "User Maintenance - Create User");
        return "userForm";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id){
        User user = service.findUserById(Integer.parseInt(id));
        service.deleteUser(user);
        return "redirect:/users";
    }

    @PostMapping(value = "/user/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("fileUpload")MultipartFile file) throws IOException {
        System.out.println("user save post invoke:::::::::::");
        System.out.println("user:: "+user.toString());
        String message;
        if(Objects.isNull(user.getId())) message = "User created successfully: "+user.getEmail();

        else{
            message = "User updated successfully: "+user.getEmail();
            if(Objects.isNull(user.getPhoto()) || Objects.isNull(user.getPassword())){
                User dbUser = service.findUserById(user.getId());
                if(Objects.isNull(user.getPassword())) user.setPassword(dbUser.getPassword());
                if(Objects.isNull(user.getPhoto())) user.setPhoto(dbUser.getPhoto());
            }
        }
        if(!file.isEmpty()){
            user.setPhoto(file.getOriginalFilename());
            System.out.println(user.toString()
            );
            User savedUser = service.saveUser(user);
           String dir = "user-photos/"+savedUser.getId();
            FileUploadUtil.saveFile(dir, file.getOriginalFilename(), file);
        } else {
            service.saveUser(user);
        }
        System.out.println(file.getOriginalFilename());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }
    @GetMapping(value = "/users/edit/{userId}")
    public String editUser(@PathVariable int userId, Model model){
        User user = service.findUserById(userId);
        List<Role> roles= service.findAllRoles();
        model.addAttribute("rolesList", roles);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "User Maintenance - Edit user | (id: "+user.getId()+")");
        System.out.println("Editing User :: "+user.toString());
        return "/userForm";
    }

    @GetMapping(value = "/users/toggle-status/{userId}")
    public String editUserStatus(@PathVariable int userId,
                                 RedirectAttributes redirectAttributes,
                                 ModelMap model){

        User user = service.findUserById(userId);
        user.setEnabled(!user.getEnabled());
        service.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "User status updated successfully for: "+user.getEmail());
        return "redirect:/users";
    }
}
