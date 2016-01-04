package net.mengkang.api.resource;

import net.mengkang.api.entity.Album;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.service.AlbumService;
import net.mengkang.api.vo.Info;
import net.mengkang.api.vo.Result;

public class AlbumResource extends BaseResource {

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


    public Object get(ApiProtocol apiProtocol) {

        int uid, aid;

        Object uidCheck = parameterIntCheck(apiProtocol, "uid");
        if (uidCheck instanceof Result) {
            return uidCheck;
        } else {
            uid = (int) uidCheck;
        }

        Object aidCheck = parameterIntCheck(apiProtocol,"aid");
        if (aidCheck instanceof Result){
            return aidCheck;
        }else {
            aid = (int) aidCheck;
        }

        AlbumService albumService = new AlbumService(apiProtocol);

        return new Result<Info>(new AlbumInfo(albumService.get(aid,uid)));
    }
}
