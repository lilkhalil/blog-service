package ru.mirea.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.mirea.domain.Like;
import ru.mirea.domain.Post;
import ru.mirea.dto.PostDto;
import ru.mirea.dto.PostRqDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(source = "likes", target = "likeCount")
    PostDto postToPostDto(Post post);
    Post createPostRqDtoToPost(PostRqDto createPostRqDto);

    default Integer map(List<Like> likes) {
        return likes.size();
    }
}
