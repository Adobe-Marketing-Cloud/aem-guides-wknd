package com.adobe.aem.guides.wknd.components.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import com.adobe.aem.guides.wknd.components.Card;
import com.adobe.cq.wcm.core.components.models.Image;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(
        adaptables = {SlingHttpServletRequest.class}, //this model can be adapted by the request
        adapters = {Card.class}, //
        resourceType = {CardImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CardImpl implements Card {

	//points to the Card component
	protected static final String RESOURCE_TYPE = "wknd/components/content/card";
	
	/**
	 * Represents the path to the article page 
	 */
	@ValueMapValue
    private String articlePath;
	
	@ValueMapValue
	private String titleOverride;
	
	@ValueMapValue
	private boolean hideDescription;
	
	/**
	 * A global variable made available by HTL script
	 */
	@ScriptVariable
	@Required
	private PageManager pageManager;
	
	/***
     * The underlying article page used to populate the card content.
     */
    private Page articlePage;
    
    //Relative location of Article Page to look for Hero Image
  	private static final String HERO_IMAGE_LOCATION = "root/image";
  	
  	@Self
  	@Required
  	private SlingHttpServletRequest request;
  		
  	@OSGiService
  	private ModelFactory modelFactory;
    
	@PostConstruct
	public void init() {
		
		if(StringUtils.isNotBlank(articlePath)) {
			articlePage = pageManager.getPage(articlePath); 
		}		
	}


	/***
	 * Order of resolution for the Title
	 * 
	 * 1. Override title (set by author dialog) if Article Page found: 2. Article
	 * Page getTitle() 3. Article Page getName() otherwise return null
	 */
	@Override
	public String getTitle() {
		String title = null;

		//if a Title Override is found use that
		if (StringUtils.isNotBlank(titleOverride)) {
			return titleOverride;
		}

		//Otherwise look for the title on the Page and fall back to the name.
		if (articlePage != null) {
			title = StringUtils.isNotBlank(articlePage.getTitle()) ? articlePage.getTitle() : articlePage.getName();
		}
		return title;
	}

	@Override
	public String getDescription() {
		
		//if hideDesciption not true
		if (articlePage != null && !hideDescription) {
			return articlePage.getDescription();
		}
		return null;
	}

	@Override
	public String getLinkPath() {
		//Use the articlePage's path to populate the link for the card
		if (articlePage != null) {
			return articlePage.getPath();
		}
		return null;
	}

	@Override
	public String getImageSrc() {
		if (articlePage != null) {

			// get the resource of the hero image relative to the article page
			Resource heroImgRes = articlePage.getContentResource(HERO_IMAGE_LOCATION);

			// if the resource is found
			if (heroImgRes != null) {
				// Adapt the resource using the Core Image component
				// com.adobe.cq.wcm.core.components.models.Image
				// since the Core Image component can only be adapted from a
				// SlingHttpServletRequest we need to use the
				// modelFactory to wrap the request.
				Image img = modelFactory.getModelFromWrappedRequest(request, heroImgRes, Image.class);

				if (img != null) {
					// use Image model to return the src attribute
					return img.getSrc();
				}
			}

		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		//if the articlePage is non null then the component is not empty
		if (articlePage != null) {
			return false;
		}
		
		//if the articlePage cannot be found return true
		return true;
	}

}
