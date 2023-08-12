package com.example.blogapi.Category;

import com.example.blogapi.Exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    ModelMapper modelMapper;
    public CategoryService(CategoryRepository repo,ModelMapper mapper){
        this.categoryRepository=repo;
        this.modelMapper=mapper;
    }

    public CategoryDTO addCategory(CategoryDTO categoryDTO){
        CategoryEntity categoryToBeSaved=modelMapper.map(categoryDTO,CategoryEntity.class);
        CategoryEntity categorySaved=categoryRepository.save(categoryToBeSaved);
        return modelMapper.map(categorySaved,CategoryDTO.class);
    }


    public CategoryDTO getCategory(long id) throws ResourceNotFoundException {
        CategoryEntity fetchCategory=categoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Category","ID",id)
        );
        return modelMapper.map(fetchCategory,CategoryDTO.class);
    }

    public List<CategoryDTO> getAllCategories(){
        List<CategoryEntity> allCategories=categoryRepository.findAll();
        List<CategoryDTO> categories=allCategories.stream().map((category)->modelMapper.map(category,CategoryDTO.class)).collect(Collectors.toList());
        return categories;
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO,long id ) throws ResourceNotFoundException {
        CategoryEntity category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","ID",id));
        category.setDescription(categoryDTO.getDescription());
        category.setName(categoryDTO.getName());
        category.setId(id);
        CategoryEntity updatedCategory=categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    public String deleteCategory(long id) throws ResourceNotFoundException {
        CategoryEntity category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","ID",id));
        categoryRepository.delete(category);
        return "Deleted Category Successfully";
    }
}
