package com.adobe.aem.guides.wknd.core.components.impl;

import static org.junit.Assert.*;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import io.wcm.testing.mock.aem.junit.AemContext;
import com.adobe.aem.guides.wknd.core.components.Byline;
import com.adobe.cq.wcm.core.components.models.Image;
import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class BylineImplTest {

	@Rule
	public final AemContext ctx = new AemContext();
	
	@Mock
	private Image image;
	
	@Mock 
	private ModelFactory modelFactory;
	
	@Before
	public void setUp() throws Exception {
		ctx.addModelsForClasses(BylineImpl.class);
          
		ctx.load().json("/com/adobe/aem/guides/wknd/core/components/impl/BylineImplTest.json", "/content");
		             
		when(modelFactory.getModelFromWrappedRequest(eq(ctx.request()), 
				any(Resource.class),
				eq(Image.class))).thenReturn(image);

		ctx.registerService(ModelFactory.class, modelFactory, 
				org.osgi.framework.Constants.SERVICE_RANKING, Integer.MAX_VALUE);
	}

	@Test
	public void testGetName() {
		final String expected = "Jane Doe";
	
	    ctx.currentResource("/content/byline");
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    String actual = byline.getName();
	
	    assertEquals(expected, actual);
	}

	@Test
	public void testGetOccupations() {
	    List<String> expected = new ImmutableList.Builder<String>()
	                            .add("Blogger")
	                            .add("Photographer")
	                            .add("YouTuber")
	                            .build();

	    ctx.currentResource("/content/byline");
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    List<String> actual = byline.getOccupations();
	    
	    assertEquals(expected, actual);
	}
	
	@Test
	public void testGetOccupations_WithoutOccupations() {
	    List<String> expected = Collections.emptyList();

	    ctx.currentResource("/content/empty");
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    List<String> actual = byline.getOccupations();
	    
	    assertEquals(expected, actual);
	}
	
	@Test
	public void testIsEmpty() {
	    ctx.currentResource("/content/empty");
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    assertTrue(byline.isEmpty());
	}
	
	@Test
	public void testIsEmpty_WithoutName() {
	    ctx.currentResource("/content/without-name");
	    
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    assertTrue(byline.isEmpty());
	}
	
	@Test
	public void testIsEmpty_WithoutOccupations() {
	    ctx.currentResource("/content/without-occupations");
	    
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    assertTrue(byline.isEmpty());
	}
	
	@Test
	public void testIsEmpty_WithEmptyArrayOfOccupations() {
	    ctx.currentResource("/content/without-occupations-empty-array");
	    
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    assertTrue(byline.isEmpty());
	}
	
	@Test
	public void testIsEmpty_WithoutImage() {
	    ctx.currentResource("/content/byline");

	    when(modelFactory.getModelFromWrappedRequest(eq(ctx.request()), 
				any(Resource.class),
				eq(Image.class))).thenReturn(null);
	    	    
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    assertTrue(byline.isEmpty());
	}
	
	@Test
	public void testIsEmpty_WithoutImageSrc() {
	    ctx.currentResource("/content/byline");
	    
	    when(image.getSrc()).thenReturn("");
	    
	    Byline byline = ctx.request().adaptTo(Byline.class);
	    
	    assertTrue(byline.isEmpty());
	}
		
	@Test
	public void testIsNotEmpty() {
		ctx.currentResource("/content/byline");
		
		when(image.getSrc()).thenReturn("/content/bio.png");
		
		Byline byline = ctx.request().adaptTo(Byline.class);
		
		assertFalse(byline.isEmpty());
	}
}