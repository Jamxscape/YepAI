package com.yep.yepai.tuling;

import java.util.List;

/**
 * Created by 舍长 on 2018/5/10.
 * 描述: 接受的Json数据实体类
 */

public class Take {
    /**
     * emotion : {"robotEmotion":{"a":0,"d":0,"emotionId":0,"p":0},"userEmotion":{"a":0,"d":0,"emotionId":0,"p":0}}
     * intent : {"actionName":"","code":10004,"intentName":""}
     * results : [{"groupType":0,"resultType":"text","values":{"text":"叫我阿紫就可以了"}}]
     */

    private EmotionBean emotion;
    private IntentBean intent;
    private List<ResultsBean> results;

    public EmotionBean getEmotion() {
        return emotion;
    }

    public void setEmotion(EmotionBean emotion) {
        this.emotion = emotion;
    }

    public IntentBean getIntent() {
        return intent;
    }

    public void setIntent(IntentBean intent) {
        this.intent = intent;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class EmotionBean {
        /**
         * robotEmotion : {"a":0,"d":0,"emotionId":0,"p":0}
         * userEmotion : {"a":0,"d":0,"emotionId":0,"p":0}
         */

        private RobotEmotionBean robotEmotion;
        private UserEmotionBean userEmotion;

        public RobotEmotionBean getRobotEmotion() {
            return robotEmotion;
        }

        public void setRobotEmotion(RobotEmotionBean robotEmotion) {
            this.robotEmotion = robotEmotion;
        }

        public UserEmotionBean getUserEmotion() {
            return userEmotion;
        }

        public void setUserEmotion(UserEmotionBean userEmotion) {
            this.userEmotion = userEmotion;
        }

        public static class RobotEmotionBean {
            /**
             * a : 0
             * d : 0
             * emotionId : 0
             * p : 0
             */

            private int a;
            private int d;
            private int emotionId;
            private int p;

            public int getA() {
                return a;
            }

            public void setA(int a) {
                this.a = a;
            }

            public int getD() {
                return d;
            }

            public void setD(int d) {
                this.d = d;
            }

            public int getEmotionId() {
                return emotionId;
            }

            public void setEmotionId(int emotionId) {
                this.emotionId = emotionId;
            }

            public int getP() {
                return p;
            }

            public void setP(int p) {
                this.p = p;
            }
        }

        public static class UserEmotionBean {
            /**
             * a : 0
             * d : 0
             * emotionId : 0
             * p : 0
             */

            private int a;
            private int d;
            private int emotionId;
            private int p;

            public int getA() {
                return a;
            }

            public void setA(int a) {
                this.a = a;
            }

            public int getD() {
                return d;
            }

            public void setD(int d) {
                this.d = d;
            }

            public int getEmotionId() {
                return emotionId;
            }

            public void setEmotionId(int emotionId) {
                this.emotionId = emotionId;
            }

            public int getP() {
                return p;
            }

            public void setP(int p) {
                this.p = p;
            }
        }

        @Override
        public String toString() {
            return "EmotionBean{" +
                    "robotEmotion=" + robotEmotion +
                    ", userEmotion=" + userEmotion +
                    '}';
        }
    }

    public static class IntentBean {

        @Override
        public String toString() {
            return "IntentBean{" +
                    "actionName='" + actionName + '\'' +
                    ", code=" + code +
                    ", intentName='" + intentName + '\'' +
                    '}';
        }

        /**
         * actionName :
         * code : 10004
         * intentName :
         */

        private String actionName;
        private int code;
        private String intentName;

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getIntentName() {
            return intentName;
        }

        public void setIntentName(String intentName) {
            this.intentName = intentName;
        }
    }

    public static class ResultsBean {
        /**
         * groupType : 0
         * resultType : text
         * values : {"text":"叫我阿紫就可以了"}
         */

        private int groupType;
        private String resultType;
        private ValuesBean values;

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
            this.groupType = groupType;
        }

        public String getResultType() {
            return resultType;
        }

        public void setResultType(String resultType) {
            this.resultType = resultType;
        }

        public ValuesBean getValues() {
            return values;
        }

        public void setValues(ValuesBean values) {
            this.values = values;
        }

        public static class ValuesBean {
            /**
             * text : 叫我阿紫就可以了
             */

            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "groupType=" + groupType +
                    ", resultType='" + resultType + '\'' +
                    ", values=" + values +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Take{" +
                "emotion=" + emotion +
                ", intent=" + intent +
                ", results=" + results +
                '}';
    }
}
