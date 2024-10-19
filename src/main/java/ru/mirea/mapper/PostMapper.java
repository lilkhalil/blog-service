package ru.mirea.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(source = "likes", target = "numberOfLikes")
    PostDto postToPostDto(Post post);

    default Integer map(List<Like> likes) {
        return likes.size();
    }
}
