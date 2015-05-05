package com.zzy.redis;
import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;
public class JedisDemo {  

    @SuppressWarnings("unchecked") 
    public void testDeom(){

        Jedis  jedis = new Jedis ("localhost",6379);//连接redis 
        // 向key-->name中放入了value-->minxr  
        jedis.set("name", "minxr");  
        String ss = jedis.get("name");  
        System.out.println(ss);  

        // 很直观，类似map 将jintao append到已经有的value之后  
        jedis.append("name", "jintao");  
        ss = jedis.get("name");  
        System.out.println(ss);  

        // 2、直接覆盖原来的数据  
        jedis.set("name", "jintao");  
        System.out.println(jedis.get("jintao"));  

        // 删除key对应的记录  
        jedis.del("name");  
        System.out.println(jedis.get("name"));// 执行结果：null  

        /** 
         * mset相当于 jedis.set("name","minxr"); jedis.set("jarorwar","aaa"); 
         */  
        jedis.mset("name", "minxr", "jarorwar", "aaa");  
        System.out.println(jedis.mget("name", "jarorwar"));  
        
        //过期时间时间
        jedis.setex("foo", 2, "foo not exits");  
        System.out.println(jedis.get("foo"));  
        try {  
            Thread.sleep(3000);  
        } catch (InterruptedException e) {  
        }  
        System.out.println(jedis.get("foo"));  
        
    }
 
   void testList(){
  	   Jedis  jedis =JedisUtil.getInstance().getJedis("localhost",6379);
	   System.out.println(jedis.flushDB());  
       // 添加数据  
        jedis.lpush("lists", "vector");  
        jedis.lpush("lists", "ArrayList");  
        jedis.lpush("lists", "LinkedList");  
        // 数组长度  
        System.out.println( "数组长度 "+ jedis.llen("lists"));  
        // 字串  
        System.out.println("打印数组..."+jedis.lrange("lists", 0, 3));  
        
        
        // 修改列表中单个值  
        jedis.lset("lists", 0, "hello list!");  
        // 获取列表指定下标的值  
        System.out.println("修改列表中单个值  "+jedis.lindex("lists", 0)); 
        
        System.out.println("打印数组..."+jedis.lrange("lists", 0, 3));  
        
        
        // 删除列表指定下标的值  
        System.out.println("删除列表指定下标的值"+jedis.lrem("lists", 1, "vector"));  
        
        System.out.println("打印数组..."+jedis.lrange("lists", 0, 3));  
        // 删除区间以外的数据  
        System.out.println("删除区间以外的数据"+jedis.ltrim("lists", 0, 1));  
        
        System.out.println("打印数组..."+jedis.lrange("lists", 0, 3));  
        
        // 列表出栈   ,移除 并返回 第一个值 
        System.out.println("列表出栈   ,移除 并返回 第一个值   "+jedis.lpop("lists"));  
        
        System.out.println("打印数组..."+jedis.lrange("lists", 0, 3));  
   }
    
   
   private void SetOperate() { 
	   Jedis  jedis =JedisUtil.getInstance().getJedis("localhost",6379);
       System.out.println("======================set=========================="); 
       // 清空数据 
       System.out.println("清空库中所有数据："+jedis.flushDB());
       
       System.out.println("=============增=============");
       System.out.println("向sets集合中加入元素element001："+jedis.sadd("sets", "element001")); 
       System.out.println("向sets集合中加入元素element002："+jedis.sadd("sets", "element002")); 
       System.out.println("向sets集合中加入元素element003："+jedis.sadd("sets", "element003"));
       System.out.println("向sets集合中加入元素element004："+jedis.sadd("sets", "element004"));
       System.out.println("查看sets集合中的所有元素:"+jedis.smembers("sets")); 
       System.out.println();
       System.out.println("=============删=============");
       System.out.println("集合sets中删除元素element003："+jedis.srem("sets", "element003"));
       System.out.println("查看sets集合中的所有元素:"+jedis.smembers("sets"));
       /*System.out.println("sets集合中任意位置的元素出栈："+jedis.spop("sets"));//注：出栈元素位置居然不定？--无实际意义
       System.out.println("查看sets集合中的所有元素:"+jedis.smembers("sets"));*/
       System.out.println();
       
       System.out.println("=============改=============");
       System.out.println();
       
       System.out.println("=============查=============");
       System.out.println("判断element001是否在集合sets中："+jedis.sismember("sets", "element001"));
       System.out.println("循环查询获取sets中的每个元素：");
       Set<String> set = jedis.smembers("sets");   
       Iterator<String> it=set.iterator() ;   
       while(it.hasNext()){   
           Object obj=it.next();   
           System.out.println(obj);   
       }  
       System.out.println();
       
       System.out.println("=============集合运算=============");
       System.out.println("sets1中添加元素element001："+jedis.sadd("sets1", "element001")); 
       System.out.println("sets1中添加元素element002："+jedis.sadd("sets1", "element002")); 
       System.out.println("sets1中添加元素element003："+jedis.sadd("sets1", "element003")); 
       System.out.println("sets1中添加元素element002："+jedis.sadd("sets2", "element002")); 
       System.out.println("sets1中添加元素element003："+jedis.sadd("sets2", "element003")); 
       System.out.println("sets1中添加元素element004："+jedis.sadd("sets2", "element004"));
       System.out.println("查看sets1集合中的所有元素:"+jedis.smembers("sets1"));
       System.out.println("查看sets2集合中的所有元素:"+jedis.smembers("sets2"));
       System.out.println("sets1和sets2交集："+jedis.sinter("sets1", "sets2"));
       System.out.println("sets1和sets2并集："+jedis.sunion("sets1", "sets2"));
       System.out.println("sets1和sets2差集："+jedis.sdiff("sets1", "sets2"));//差集：set1中有，set2中没有的元素
       
   }
    
    
    public static void main(String[] args)  throws Exception{         
        JedisDemo jedis = new JedisDemo();  
       // jedis.testDeom();  
       // jedis.testKey();
        jedis.testList();
    }  
  
}