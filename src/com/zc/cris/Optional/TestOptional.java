package com.zc.cris.Optional;

import com.zc.cris.Employee;
import org.junit.Test;

import java.util.Optional;

/*
 * 一、Optional 容器类：用于尽量避免空指针异常，快速定位null 值
 * 	Optional.of(T t) : 创建一个 Optional 实例,不允许 t 为null
 * 	Optional.empty() : 创建一个空的 Optional 实例
 * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * 	isPresent() : 判断是否包含值
 * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional，进一步防止空指针异常
 */
public class TestOptional {

    // 案例分析：拿到一个男人心中的女神的名字(三种情况：传入的man为null；传入的man不为空，但是其女神为null；传入的man及其女神都不为null)
    @Test
    public void test4() {
        /*Man man = new Man();
        String godnessName = getGodnessName(man);
        System.out.println(godnessName);*/

        String godnessName = getGodnessName(null);
        System.out.println(godnessName);    // 桃谷绘里香
        String godnessName1 = getGodnessName(new Man());
        System.out.println(godnessName1);   // 苍井空


    }

    public String getGodnessName(Man man) {
        // 以前为了防止空指针的写法
        /*if(man != null){
            Godness godness = man.getGodness();
            if(godness != null){
                return godness.getName();
            }
        }
        return "桥本有菜";*/

        // 第二种写法(这么写有点麻烦，还是使用第三种写法简化)
        /*Optional<Man> optional = Optional.ofNullable(man);
        if (!optional.isPresent()) {   // 如果容器中的man 对象为null
           return "泷泽萝拉";

        } else {     // 如果容器中的man 不为空，但是man 的godNess 为空，这里可以为其指定一个女神对象，或者返回一个自定义字符串"屌丝没有女神"
            Man man1 = optional.get();
            Optional<Godness> optional1 = Optional.ofNullable(man1.getGodness());
            // 如果man没有女神，就给他搞一个默认的全民女神
            Godness godness = optional1.orElse(new Godness("苍井空"));
            return godness.getName();
        }*/

        // 第三种写法
        // 避免 man为null
        Godness godness = Optional.ofNullable(man)
                .orElse(new Man())
                .getGodness();
        // 避免 man的godNess为null
        return Optional.ofNullable(godness)
                .orElse(new Godness("泷泽萝拉"))
                .getName();

        // 第四种写法：需要改造Man，将其godNess属性用optional 包装一下（因为不是所有的男人都有女神），见test5
    }

    @Test
    public void test5() {
        NewMan man = new NewMan();
//        Optional<Godness> optionalGodness = Optional.ofNullable(null);
        Optional<Godness> optionalGodness = Optional.ofNullable(new Godness("三上悠亚"));
        man.setGodness(optionalGodness);
        String newManGodnessName = getNewManGodnessName(man);
        System.out.println(newManGodnessName);
    }

    public String getNewManGodnessName(NewMan man) {
        // 逻辑：客户端只需要传入 NewMan的对象，如果为null，创建一个新的NewMan对象，并返回自创的godness对象
        // 如果客户端传入的 NewMan的对象不为null，那么判断其女神是否为null，如果女神不为null，返回该女神，如果女神为null，返回自建的女神
        return Optional.ofNullable(man)
                .orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("柚木提娜"))
                .getName();
    }

    @Test
    public void test3() {
        Optional<Employee> optional = Optional.ofNullable(new Employee("cris", 23));

        // 可以通过功能性接口对放在 optional 容器中的对象进行处理
        Optional<String> s = optional.map(employee -> employee.getName());
        System.out.println(s.get());

        Optional<String> s1 = optional.flatMap(employee -> Optional.ofNullable(employee.getName()));
        System.out.println(s1.get());


    }


    @Test
    public void test2() {
//        Optional<Employee> optional = Optional.ofNullable(new Employee());
        // 可以封装一个null 对象的optional容器
        Optional<Employee> optional = Optional.ofNullable(null);
        // 如果容器中有不为null的值
        if (optional.isPresent()) {
            System.out.println(optional.get());
        } else {
            // 如果容器中的对象为null，那么就用指定的对象来代替，有效避免空指针异常
            Employee employee = optional.orElse(new Employee("CRIS", 23));
            System.out.println(employee);

            // 和orElse 的区别在于参数是供给型接口，我们可以使用lambda 表达式来进行各种定制化或者逻辑判断等
            Employee cris = optional.orElseGet(() -> {
                System.out.println("cirs, let's go");
                return new Employee("cris", 22);
            });
            System.out.println(cris);
        }

    }

    @Test
    public void test1() {
        // 构建一个空的optional 容器对象
        Optional<Employee> empty = Optional.empty();
//        System.out.println(empty);                // Optional.empty

//        System.out.println(empty.get());        // 取空值报错
    }

    @Test
    public void test() {
        // 往optional 容器对象中封装一个对象
        Optional<Employee> optional = Optional.of(new Employee("cris", 23));
        System.out.println(optional.get());

        // 使用 Optional 来封装为null 的对象，可以很快的定位null 值（因为不允许封装对象为null）
        Optional<Employee> optional1 = optional = Optional.of(null);    // 直接报错
        System.out.println(optional1);
    }

}
