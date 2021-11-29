package diaminho.spotify.mapper;

import diaminho.spotify.dto.SongDto;
import diaminho.spotify.model.spotify.Artist;
import diaminho.spotify.model.spotify.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

/**
 * Mapper for Spotify Item model to SongDto
 */
@Mapper(componentModel = "spring", imports = {Collectors.class, Artist.class})
public interface SongDtoMapper {
    @Mapping(expression = "java(item.getTrack().getName())", target = "name")
    @Mapping(expression = "java(item.getTrack().getArtists().stream().map(Artist::getName).collect(Collectors.toList()).toString().replaceAll(\"\\\\[|\\\\]\", \"\"))", target = "author")
    SongDto itemToSongDto(Item item);
}
