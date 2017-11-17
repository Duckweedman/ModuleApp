package com.test.functionlibrary.jpush;

/**
 * 推送的消息VO
 *
 * @author fengjunqin
 */
public class MessageVO {
    /**
     * message : {"contents":"gfd","linksAddress":"","oid":111,"pictureAddress":"das","timeline":1505964337766,"title":"ds"}
     */

    public MessageBean message;

    public static class MessageBean {
        /**
         * contents : gfd
         * linksAddress :
         * oid : 111
         * pictureAddress : das
         * timeline : 1505964337766
         * title : ds
         */
        /**
         * ID
         */
        public long oid;

        /**
         * 消息标题
         */
        public String title;

        /**
         * 消息内容
         **/
        public String contents;

        /**
         * 图片地址
         **/
        public String pictureAddress;

        /**
         * 链接地址
         **/
        public String linksAddress;

        /**
         * 发送时间
         **/
        public long timeline;
    }
}
