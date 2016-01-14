package net.mengkang.demo.bo;

import net.mengkang.demo.entity.Album;
import net.mengkang.nettyrest.response.Info;

/**
 * Created by zhoumengkang on 5/1/16.
 */
public class AlbumInfo extends Info {
    private Album album;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public AlbumInfo(Album album) {
        this.album = album;
    }
}
