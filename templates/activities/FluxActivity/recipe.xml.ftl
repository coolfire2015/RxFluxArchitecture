<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>

<merge from="root/AndroidManifest.xml.ftl"
	to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

<instantiate from="root/src/app_package/FluxActivity.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/view/${activityClass}.kt" />
<open file="${escapeXmlAttribute(srcOut)}/${activityClass}.kt" />

<instantiate from="root/src/app_package/FluxFragment.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/view/${fragmentClass}.kt" />

<instantiate from="root/src/app_package/FluxActivityModule.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/module/${activityModuleClass}.kt" />

<instantiate from="root/src/app_package/FluxStore.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/store/${storeClass}.kt" />

<instantiate from="root/src/app_package/FluxAction.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/action/${actionClass}.kt" />

<instantiate from="root/src/app_package/FluxActionCreator.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/action/${actionCreatorClass}.kt" />

<#if generateFragmentLayout>
    <instantiate from="root/res/layout/fragment.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

</recipe>
