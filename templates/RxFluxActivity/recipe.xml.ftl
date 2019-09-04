<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>

<merge from="root/AndroidManifest.xml.ftl"
	to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

<instantiate from="root/src/app_package/RxFluxActivity.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/view/${activityClass}.kt" />
<open file="${escapeXmlAttribute(srcOut)}/${activityClass}.kt" />

<instantiate from="root/src/app_package/RxFluxFragment.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/view/${fragmentClass}.kt" />

<instantiate from="root/src/app_package/RxFluxModule.kt.ftl"
        to="${escapeXmlAttribute(srcOut)}/module/${moduleClass}.kt" />

<instantiate from="root/src/app_package/RxFluxStore.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/store/${storeClass}.kt" />

<instantiate from="root/src/app_package/RxFluxAction.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/action/${actionClass}.kt" />

<instantiate from="root/src/app_package/RxFluxActionCreator.kt.ftl"
    to="${escapeXmlAttribute(srcOut)}/action/${actionCreatorClass}.kt" />

<#if generateFragmentLayout>
    <instantiate from="root/res/layout/fragment.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

</recipe>
