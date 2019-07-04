package androidx.databinding;

/**
 * 解决模块化中DataBinding单元测试编译文件生成问题
 */
public class DataBinderMapperImpl extends MergedDataBinderMapper {
    DataBinderMapperImpl() {
        //添加实际编译生成的DataBinderMapperImpl文件
        addMapper(new com.huyingbao.module.github.DataBinderMapperImpl());
    }
}