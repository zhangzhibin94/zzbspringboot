var sso =  angular.module('sso',[]);
sso.controller('LoginController',['$scope','$interval','$http',function ($scope,$interval,$http) {
    $scope.vm={
        loginType:'username',//默认登录类型为账号密码登录
        autoLogin:false,//默认不勾选自动登录
        codeMsg:'获取',
        codeMsg2:'获取',
        register:false,
        registerSuccess:false//注册成功
    }
    $scope.register = {
        telephoneError:'',
        checkPwdError:'',
        checkCodeError:''
    }
    $scope.login = {
        telephoneError:'',
        checkPwdError:'',
        checkCodeError:''
    }

    $scope.second=59;
    $scope.second2=59;
    $scope.changeTab=function (status) {
        $scope.vm.loginType=status;
        if(status==''){

        }
    }
    $scope.checkAuto = function (status) {
        $scope.vm.autoLogin = !status;
    }
    //发送短信
    $scope.getCode = function() {
        if($scope.vm.register==true){//注册调用此方法
            $scope.register.telephoneError=null;
            $scope.register.checkPwdError=null;
            $scope.register.checkCodeError=null;
            $scope.register.usernameError = null;
            if(!$scope.register.username){
                $scope.register.usernameError = '请填入用户名';
                return;
            }
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
        }else if($scope.vm.register==false){
            $scope.login.telephoneError=null;
            $scope.login.checkPwdError=null;
            $scope.login.checkCodeError=null;
            //验证手机号
            if($scope.login.telephone){
                var isOkTel = $scope.checkPhone($scope.login.telephone);
                if(!isOkTel){
                    $scope.login.telephoneError = '您填写的手机号格式错误！';
                    return false;
                }
            }else {
                $scope.login.telephoneError = '请先填写您的手机号码！';
                return false;
            }
            $scope.canClick2 = false;
            //倒计时60s
            timerHandler = $interval(function () {
                if ($scope.second2 <= 0) {
                    $interval.cancel(timerHandler);    //当执行的时间59s,结束时，取消定时器 ，cancle方法取消
                    $scope.second2 = 59;
                    $scope.vm.codeMsg2 = "获取验证码";
                    $scope.canClick2 = false;    //因为 ng-disabled属于布尔值，设置按钮可以点击，可点击发送
                } else {
                    $scope.vm.codeMsg2 = "("+$scope.second2 + "s)";
                    $scope.second2--;
                    $scope.canClick2 = true;
                }
            }, 1000);
        }

        //发送验证码
        if($scope.vm.register==true){
            $scope.checkCode = {
                telephone:$scope.register.telephone,
                type:'register'
            }
            $http({
                method:'post',
                url:'/send_check_code',
                data:$scope.checkCode,
            }).then(function(data){
                if (data.data.errors == null || data.data.errors.length > 0) {
                    $scope.register.checkCodeError=data.data.firstErrorMessage;
                } else {
                    $scope.result = data.data;
                }
            })
        }else if($scope.vm.register==false){
            $scope.loginCode = {
                telephone:$scope.login.telephone,
                type:'login'
            }
            $http({
                method:'post',
                url:'/send_check_code',
                data:$scope.loginCode,
            }).then(function(data){
                if (data.data.errors == null || data.data.errors.length > 0) {
                    $scope.login.checkCodeError=data.data.firstErrorMessage;
                } else {
                    $scope.result = data.data;
                }
            })
        }
    }



    //用户注册
    $scope.doRegister = function () {
        $scope.register.telephoneError=null;
        $scope.register.checkPwdError=null;
        $scope.register.checkCodeError=null;
        $scope.register.usernameError = null;
        if(!$scope.register.username){
            $scope.register.usernameError = '请填入用户名';
            return;
        }
        if($scope.register){
            if($scope.register.telephone){
                var isOkTel = $scope.checkPhone($scope.register.telephone);
                if(!isOkTel){
                    $scope.register.telephoneError = '您填写的手机号格式错误！';
                    return false;
                }
                $scope.register.telephone = Number($scope.register.telephone);

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
        $scope.register.code = $scope.register.code+'';
        $http({
            method:'post',
            url:'/register',
            data:$scope.register,
        }).then(function(data){
            if (data.data.errors == null || data.data.errors.length > 0) {
                $scope.register.checkCodeError=data.data.firstErrorMessage;
            }else{
                $scope.result = data.data;
                $scope.url = $scope.result.url;
                $scope.vm.registerSuccess=true;
            }
        })
    }
    //检查手机号格式
    $scope.checkPhone = function(telephoneNumber){
        if(!(/^1[34578]\d{9}$/.test(telephoneNumber))){
            return false;
        }
        return true;
    }
    //检查两次密码是否相同
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
    //用户登录
    $scope.doLogin = function () {
        $scope.login.telephoneError=null;
        $scope.login.checkPwdError=null;
        $scope.login.checkCodeError=null;
        if($scope.vm.loginType){
            if($scope.vm.loginType=='phone'){//如果登录方式是手机号登录
                $scope.login.loginType='telephone';
                var isOkTel = $scope.checkPhone($scope.login.telephone);
                if(!isOkTel){
                    $scope.login.telephoneError = '您填写的手机号格式错误！';
                    return false;
                }
                if(!$scope.login.code){
                    $scope.login.checkCodeError = '请填写验证码';
                    return;
                }
            }else{
                alert("暂未开通账号密码登录,请通过手机号验证码登录")
                return false;
            }
        }
        $http({
            method:'post',
            url:'/login_in',
            data:$scope.login,
        }).then(function(data){
            if (data.data.errors == null || data.data.errors.length > 0) {
                $scope.login.checkCodeError=data.data.firstErrorMessage;
            }else{
                // $rootScope.user = data.data.user;
                window.location.href="/index.html";
            }
        })
    }
    //前往登录
    $scope.doLoginPage = function () {
        window.location.href="/login.html"
    }
    $scope.returnIndex = function () {
        window.location.href="/index.html"
    }
}]);