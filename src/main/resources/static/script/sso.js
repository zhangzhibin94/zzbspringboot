var sso =  angular.module('sso',[]);
sso.controller('LoginController',['$scope','$interval','$http',function ($scope,$interval,$http) {
    $scope.vm={
        loginType:'username',//默认登录类型为账号密码登录
        autoLogin:false,//默认不勾选自动登录
        codeMsg:'获取',
        register:false
    }
    $scope.register = {
        telephoneError:'',
        checkPwdError:''
    }

    $scope.second=59;
    $scope.changeTab=function (status) {
        $scope.vm.loginType=status;
    }
    $scope.checkAuto = function (status) {
        $scope.vm.autoLogin = !status;
    }
    //发送短信
    $scope.getCode = function() {
        $scope.register.telephoneError=null;
        $scope.register.checkPwdError=null;
        $scope.register.checkCodeError=null;
        if($scope.vm.register==true){//注册调用此方法
            //验证手机号
            if($scope.register.telephone){
                var isOkTel = $scope.checkPhone($scope.register.telephone);
                if(!isOkTel){
                    $scope.register.telephoneError = '您填写的手机号格式错误！';
                    return false;
                }
            }else {
                $scope.register.telephoneError = '请先填写您的手机号码！';
                return false;
            }
            //验证密码
            var checkPwd = $scope.checkPwdConfirm($scope.register.password,$scope.register.confirmPwd);
            if(!checkPwd){
                $scope.register.checkPwdError = '您二次输入的密码不一致，请重新输入';
                return false;
            }


        }
        $scope.canClick = false;
        //倒计时60s
        timerHandler = $interval(function () {
            if ($scope.second <= 0) {
                $interval.cancel(timerHandler);    //当执行的时间59s,结束时，取消定时器 ，cancle方法取消
                $scope.second = 59;
                $scope.vm.codeMsg = "获取验证码";
                $scope.canClick = false;    //因为 ng-disabled属于布尔值，设置按钮可以点击，可点击发送
            } else {
                $scope.vm.codeMsg = "("+$scope.second + "s)";
                $scope.second--;
                $scope.canClick = true;
            }
        }, 1000);
        //发送验证码
        if($scope.vm.register==true){
            $scope.checkCode = {
                telephone:$scope.register.telephone,
            }
            $http({
                method:'post',
                url:'/send_check_code',
                data:$scope.checkCode,
            }).then(function(data){
                if (data.errors == null || data.errors.length > 0) {
                    $scope.register.checkCodeError=data.errors;
                } else {
                    $scope.result = data.data;
                    $scope.url = $scope.result.url;
                }
            })
        }
    }
    //用户注册
    $scope.register = function () {
        $scope.register.telephoneError=null;
        $scope.register.checkPwdError=null;
        $scope.register.checkCodeError=null;
        if($scope.register){
            if($scope.register.telephone){
                var isOkTel = $scope.checkPhone($scope.register.telephone);
                if(!isOkTel){
                    $scope.register.telephoneError = '您填写的手机号格式错误！';
                    return false;
                }

            }else {
                $scope.register.telephoneError = '请先填写您的手机号码！';
                return false;
            }
        }
        var checkPwd = $scope.checkPwdConfirm($scope.register.password,$scope.register.confirmPwd);
        if(!checkPwd){
            $scope.register.checkPwdError = '您二次输入的密码不一致或为输入密码，请重新输入';
            return false;
        }
        if(!$scope.register.code){
            $scope.register.checkCodeError='请输入验证码';
            return false;
        }
        $http({
            method:'post',
            url:'/register',
            data:$scope.register,
        }).then(function(data){
            $scope.result = data.data;
            $scope.url = $scope.result.url;

        })
    }
    $scope.checkPhone = function(telephoneNumber){
        if(!(/^1[34578]\d{9}$/.test(telephoneNumber))){
            return false;
        }
        return true;
    }
    $scope.checkPwdConfirm = function(pwd1,pwd2){
        if(!pwd1||!pwd2){
            return false;
        }
        if(pwd1!=pwd2){
            return false;
        }
        return true;
    }
    //注意，前台的倒计时卡控不靠谱，需要在后台调用redis的expire进行二次卡控
}]);