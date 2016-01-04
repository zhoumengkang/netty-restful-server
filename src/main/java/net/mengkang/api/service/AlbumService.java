package net.mengkang.api.service;

import net.mengkang.api.entity.Album;
import net.mengkang.api.entity.User;
import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.bo.Star;

/**
 * Created by zhoumengkang on 4/1/16.
 */
public class AlbumService extends BaseService {

    public AlbumService(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    public Album get(int id, int uid) {
        User   user  = new User(uid, "mengkang", 25);
        String cover = "http://static.mengkang.net/view/images/avatar/1.jpg";
        Star   star  = new Star(120, true);

        return new Album(id, user, cover, star);
    }
}
