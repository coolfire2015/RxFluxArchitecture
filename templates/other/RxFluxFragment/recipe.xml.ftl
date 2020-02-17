<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>

<instantiate from="root/src/app_package/RxFluxFragment.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/view/${fragmentClass}.kt" />

<#if generateFragmentLayout>
    <instantiate from="root/res/layout/fragment.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

</recipe>
