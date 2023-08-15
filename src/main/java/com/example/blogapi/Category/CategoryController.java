package com.example.blogapi.Category;

import com.example.blogapi.Exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name="CRUD REST APIs for Category Resource")
public class CategoryController {
    CategoryService categoryService;
    public CategoryController(CategoryService cs){
        this.categoryService=cs;
    }

    @Operation(
            summary="Create Categories REST API",
            description="Create categories REST API is used to save categories in the database."+
                         " Please note you won't be able to edit the category without ADMIN privileges so enter the" +
                    "category accurately."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTT Status 201 Created"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory=categoryService.addCategory(categoryDTO);
        return new ResponseEntity(savedCategory, HttpStatus.CREATED);
    }

    @Operation(
            summary="Get Category By Id REST API",
            description="Get Category By ID REST API is used to fetch the category with the given id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable long id) throws ResourceNotFoundException {
        CategoryDTO fetchedCategory=categoryService.getCategory(id);
        return ResponseEntity.ok(fetchedCategory);
    }

    @Operation(
            summary="Get All Categories REST API",
            description="Get all categories REST API is used to fetch all the categories from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> allCategories=categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @Operation(
            summary="Update Category REST API",
            description="Update Category REST API is used to edit the category with the given id and save the updated category in the database"+
                    "You need to have ADMIN privileges to update a category."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable long id) throws ResourceNotFoundException {
        CategoryDTO updatedCategory=categoryService.updateCategory(categoryDTO,id);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(
            summary="Delete Categories REST API.",
            description="Delete Category REST API is used to delete the category with the given id from the database." +
                    "You need to have ADMIN privileges to delete a category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteCategory(@PathVariable  long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
