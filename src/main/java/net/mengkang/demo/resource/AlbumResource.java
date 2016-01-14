package net.mengkang.demo.resource;

import net.mengkang.demo.bo.AlbumInfo;
import net.mengkang.nettyrest.ApiProtocol;
import net.mengkang.demo.service.AlbumService;
import net.mengkang.nettyrest.response.Info;
import net.mengkang.nettyrest.response.Result;
import net.mengkang.nettyrest.BaseResource;

public class AlbumResource extends BaseResource {

    public AlbumResource(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    public Result get() {

        int uid, aid;

        Object uidCheck = parameterIntCheck(apiProtocol, "uid");
        if (uidCheck instanceof Result) {
            return (Result) uidCheck;
        } else {
            uid = (int) uidCheck;
        }

        Object aidCheck = parameterIntCheck(apiProtocol,"aid");
        if (aidCheck instanceof Result){
            return (Result) aidCheck;
        }else {
            aid = (int) aidCheck;
        }

        AlbumService albumService = new AlbumService(apiProtocol);

        return new Result<Info>(new AlbumInfo(albumService.get(aid,uid)));
    }
}
