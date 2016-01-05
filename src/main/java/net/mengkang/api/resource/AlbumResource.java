package net.mengkang.api.resource;

import net.mengkang.api.bo.AlbumInfo;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.service.AlbumService;
import net.mengkang.api.response.Info;
import net.mengkang.api.response.Result;

public class AlbumResource extends BaseResource {

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
