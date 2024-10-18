package ru.mirea.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    PostDto postToPostDto(Post post);
}
