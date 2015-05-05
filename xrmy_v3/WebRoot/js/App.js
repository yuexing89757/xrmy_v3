(function(){
    var domain="http://"+window.location.host;
    var newsApi = domain+'/api/email/select/news.json';
    var myApp = angular.module("myApp", ['ui.router']);
    myApp.config(function($stateProvider, $urlRouterProvider,$sceDelegateProvider) {
            $sceDelegateProvider.resourceUrlWhitelist(['self','http://player.youku.com/player.php/sid/**']);
            $urlRouterProvider.when("", "home");

            $stateProvider.state('space', {
                    url:'/{spaceName:home|about|company|news|product|call}',
                    templateUrl: function($stateParams){
                        var spaceName = $stateParams.spaceName;
                        var urlMap = {
                            home:'page0.html',
                            about:'about_us.html',
                            company:'honer.html',
                            news:'news_center.html',
                            product:'product-list.html',
                            call:'call_us.html'
                        }
                        return urlMap[spaceName]
                    },
                    controllerProvider: function($stateParams) {
                        var ctrlName = $stateParams.spaceName;
                        var controllerName = {
                            home:'homeCtrl',
                            about:'aboutCtrl',
                            company:'companyCtrl',
                            news:'newsCtrl',
                            product:'productCtrl',
                            call:'callCtrl'                            
                        }
                        return controllerName[ctrlName];
                    }
            })
            
            // 新闻列表
            $stateProvider.state("space.link", {

                url: "/{link:company|video|trend|advance}",
                views: {
                    'homeMain': {
                        templateUrl: function($stateParams) {
                            if($stateParams.link=='video'||$stateParams.link=='advance'){
                                return 'product.html';
                            }else{
                                return 'new_list.html';
                            }
                            
                        },
                        controllerProvider: function($stateParams) {
                            var link = $stateParams.link;
                            var map = {
                                company:'newsList',
                                trend:'newsList',
                                advance:'productCtrl',
                                video:'productCtrl'
                            }

                            return map[link];
                        }

                    }
                }
            })
            
            // 新闻内容
            $stateProvider.state("space.news", {

                url: "/{idx:[0-9]+}",
                views: {
                    'homeMain': {

                        templateUrl: function($stateParams) {

                            return 'news.html'
                        },
                        controller: 'newsDetail'
                    }
                }

            })

            $stateProvider.state("space.prd",{
                url:'/detail/{id:[0-9]+}',
                views:{
                    '@':{
                        templateUrl:'prd_detail.html',
                        controller:'prdDetail'
                    }
                }
                
            })

    });

    myApp.run(function($rootScope){
        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            var mapText = {
                company:'公司新闻',
                trend:'行业动态',
                advance:'新品预告',
                video:'精品推荐'
            }            
            $rootScope.activeTab =toParams.spaceName;
            $rootScope.link=toParams.link;
            $rootScope.linkName=mapText[toParams.link];            
        });
    })

    myApp.controller('homeCtrl',['$scope','$stateParams','$rootScope',function($scope,$stateParams,$rootScope){
        
        $rootScope.activeTab = $stateParams.spaceName;  
        $.ajax({
            url:newsApi,
            type:'post',
            data:JSON.stringify( {
                locale: 'zh_cn',
                network: 'GOOGLE',
                params: {
                    type:'COMPANY' // 新闻类型
                }
            }),
            dataType: 'json',
            success: function(data) {
                $scope.homeList = data.result;
                $scope.$apply();
            }
        })
        $scope.init = function(){
            jQuery(".picScroll").slide({ mainCell:"ul", effect:"leftMarquee", vis:4, autoPlay:true, interTime:50, switchLoad:"_src" });
        }
        // jQuery(".focusBox").slide({ mainCell:".pic",effect:"left", autoPlay:true, delayTime:300});
        
        // $scope.homeList = [
        //     {
        //         title:'2015年3月18日猪评：北方猪价稳定南方略上调',
        //         time:'2015-01-12',
        //         id:1
        //     },
        //     {
        //         title:'2015年3月18日料评：玉米深加工收购价跌涨互现',
        //         time:'2015-01-12',
        //         id:2
        //     },
        //     {
        //         title:'市场大猪源偏紧后期猪价或将维持弱势震荡走势',
        //         time:'2015-01-12',
        //         id:3
        //     },
        //     {
        //         title:'猪价行情形势急转，成本决定养猪人的命运！',
        //         time:'2015-01-12',
        //         id:4
        //     },
        //     {
        //         title:'美国第五大养猪企业投资山东生猪育肥项目',
        //         time:'2015-01-12',
        //         id:5
        //     },
        //     {
        //         title:'2015年3月12日猪评：东北猪价微跌南方略微上调',
        //         time:'2015-01-12',
        //         id:6
        //     }
        // ]
    }])
    myApp.controller('aboutCtrl',['$scope','$stateParams','$rootScope',function($scope,$stateParams,$rootScope){
    }])
    myApp.controller('companyCtrl',['$scope','$stateParams','$rootScope',function($scope,$stateParams,$rootScope){
    }])
    myApp.controller('newsCtrl',['$scope','$stateParams','$rootScope',function($scope,$stateParams,$rootScope){
    }])
    myApp.controller('productCtrl',['$scope','$stateParams','$rootScope',function($scope,$stateParams,$rootScope){
        $rootScope.activeTab = $stateParams.spaceName;
        var ajaxParams = {
            locale: 'zh_cn',
            network: 'GOOGLE',
            params: {}
        }

        if($stateParams.link=='video'){
            ajaxParams.params.IfRecommend = true;
        }
        $.ajax({
            url:domain+'/api/email/select/product.json',
            type:'post',
            data:JSON.stringify(ajaxParams),
            dataType: 'json',
            crossDomain: true,
            success: function(data) {
                $scope.videoData = data.result;
                $scope.$apply();
            }
        }) 
       
    }]) 
    myApp.controller('callCtrl',['$scope','$stateParams','$rootScope',function($scope,$stateParams,$rootScope){
    }])

    // 视频详情
    myApp.controller('prdDetail',['$scope','$stateParams',function($scope,$stateParams){
        
        $.ajax({
            url:domain+'/api/email/select/productDetail.json',
            type:'post',
            data:JSON.stringify( {
                locale: 'zh_cn',
                network: 'GOOGLE',
                params: {
                    productId:$stateParams.id
                }
            }),
            dataType: 'json',
            crossDomain: true,
            success: function(data) {
                $scope.prdData = data.result;
                $scope.prdData.videoURL = 'http://player.youku.com/player.php/sid/'+$scope.prdData.videoURL+'/v.swf';
                var str = '<embed src="'+$scope.prdData.videoURL+'" quality="high" width="100%" height="400" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash"></embed>';  


                $('.vadio-box').html(str);                
                $scope.$apply();
            }
        })

    }])

    // 新闻列表
    myApp.controller('newsList',['$scope','$stateParams',function($scope,$stateParams){
        var link = $stateParams.link;
        var map = {
            company:'COMPANY',
            trend:'TREND',
            advance:'ADVANCE'
        }
        var mapText = {
            company:'公司新闻',
            trend:'行业动态',
            advance:'新品预告',
        }
        $.ajax({
            url:newsApi,
            type:'post',
            data:JSON.stringify( {
                locale: 'zh_cn',
                network: 'GOOGLE',
                params: {
                    type:map[link] // 新闻类型
                }
            }),
            dataType: 'json',
            crossDomain: true,
            success: function(data) {
                $scope.list = data.result;
                $scope.$apply();
            }
        })
      
    }]) 

    myApp.controller('newsDetail',['$scope','$stateParams',function($scope,$stateParams){
        var map = {
            company:'COMPANY',
            trend:'TREND',
            advance:'ADVANCE'
        }        
        $.ajax({
            url:'/api/email/select/newsDetail.json',
            type:'post',
            data:JSON.stringify( {
                locale: 'zh_cn',
                network: 'GOOGLE',
                params: {
                    newsId:$stateParams.idx // 新闻id
                }
            }),
            dataType: 'json',
            crossDomain: true,
            success: function(data) {
                $scope.content = data.result;
                $scope.$apply();
            }
        })      
    }])                 
})()

