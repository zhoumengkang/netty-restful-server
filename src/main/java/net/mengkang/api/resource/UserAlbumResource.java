package net.mengkang.api.resource;

import net.mengkang.api.handler.ApiProtocol;
import net.mengkang.api.vo.Info;
import net.mengkang.api.vo.Result;

public class UserAlbumResource extends BaseResource {

    public class getUserAlbumInfo extends Info {
        private int uid;
        private int aid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public getUserAlbumInfo(int uid, int aid) {
            this.uid = uid;
            this.aid = aid;
        }
    }


    public Object get(ApiProtocol apiProtocol) {

        int uid,aid;

        Object uidCheck = parameterIntCheck(apiProtocol,"uid");
        if (uidCheck instanceof Result){
            return uidCheck;
        }else{
            uid = (int) uidCheck;
        }

        Object aidCheck = parameterIntCheck(apiProtocol,"aid");
        if (aidCheck instanceof Result){
            return aidCheck;
        }else {
            aid = (int) aidCheck;
        }

        return new Result<Info>(new getUserAlbumInfo(uid,aid));
    }
}
