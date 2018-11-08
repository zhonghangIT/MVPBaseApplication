<?xml version="1.0"?>
<recipe>

    <#if useSupport><dependency mavenUrl="com.android.support:support-v4:${buildApi}.+"/></#if>

    <#if includeLayout>
        <instantiate from="root/res/layout/fragment_blank.xml.ftl" to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml"/>

        <open file="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml"/>
    </#if>

    <open file="${escapeXmlAttribute(srcOut)}/${className}.java"/>

    <instantiate from="root/src/app_package/MVPFragment.java.ftl" to="${escapeXmlAttribute(srcOut)}/${className}.java"/>
    <instantiate from="root/src/app_package/MVPContract.java.ftl" to="${escapeXmlAttribute(srcOut)}/${contractClass}.java"/>
    <instantiate from="root/src/app_package/MVPPresenter.java.ftl" to="${escapeXmlAttribute(srcOut)}/${presenterClass}.java"/>

</recipe>
