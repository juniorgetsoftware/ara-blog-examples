package org.aracrown.blog.s01e05;

import org.aracrown.blog.s01e05.model.BlogEntry;
import org.aracrown.blog.s01e05.resource.BlogEntryResource;
import org.junit.Assert;
import org.junit.Test;

import fr.xebia.extras.selma.Selma;

public class SelmaBlogEntryMapperTest extends AbstractTest {

	@Test
	public void testSelmaMapper() throws Exception {
		SelmaBlogEntryMapper mapper = Selma.builder(SelmaBlogEntryMapper.class).build();

		BlogEntryResource result = mapper.toResourceBlogEntry(newBlogEntry());

		Assert.assertEquals("title goes here", result.getTitle());
		Assert.assertEquals("name", result.getAuthorName());
		Assert.assertEquals("signature", result.getAuthorSignature());
		Assert.assertEquals(1, result.getId().intValue());

		Assert.assertNotNull(result.getProperties());
		Assert.assertTrue(result.getProperties().isCommentsEnabled());
		Assert.assertTrue(result.getProperties().isEnabled());

		BlogEntry reverse = mapper.toJpaBlogEntry(result);

		Assert.assertEquals("title goes here", reverse.getTitle());
		Assert.assertEquals(1, reverse.getId().intValue());
		Assert.assertTrue(reverse.isCommentsEnabled());
		Assert.assertTrue(reverse.isEnabled());

		Assert.assertNotNull(reverse.getAuthor());
		Assert.assertEquals("name", reverse.getAuthor().getName());
		Assert.assertEquals("signature", reverse.getAuthor().getSignature());

	}

	@Test
	public void testSelmaMapperNullAuthor() throws Exception {
		SelmaBlogEntryMapper mapper = Selma.builder(SelmaBlogEntryMapper.class).build();
		Assert.assertNull(mapper.toJpaBlogEntry(null));
		Assert.assertNull(mapper.toResourceBlogEntry(null));

		BlogEntry be = newBlogEntry();
		be.setAuthor(null);

		BlogEntryResource result = mapper.toResourceBlogEntry(be);
		Assert.assertNotNull(result);

		Assert.assertEquals("title goes here", result.getTitle());
		Assert.assertNull(result.getAuthorName());
		Assert.assertNull(result.getAuthorSignature());
		Assert.assertEquals(1, result.getId().intValue());

		Assert.assertNotNull(result.getProperties());
		Assert.assertTrue(result.getProperties().isCommentsEnabled());
		Assert.assertTrue(result.getProperties().isEnabled());
	}

	@Test
	public void testSelmaMapperNullAuthorName() throws Exception {
		SelmaBlogEntryMapper mapper = Selma.builder(SelmaBlogEntryMapper.class).build();

		BlogEntry be = newBlogEntry();
		be.getAuthor().setName(null);
		BlogEntryResource result = mapper.toResourceBlogEntry(be);

		Assert.assertEquals("title goes here", result.getTitle());
		Assert.assertNull(result.getAuthorName());
		Assert.assertEquals("signature", result.getAuthorSignature());
		Assert.assertEquals(1, result.getId().intValue());

		Assert.assertNotNull(result.getProperties());
		Assert.assertTrue(result.getProperties().isCommentsEnabled());
		Assert.assertTrue(result.getProperties().isEnabled());

		BlogEntry reverse = mapper.toJpaBlogEntry(result);

		Assert.assertEquals("title goes here", reverse.getTitle());
		Assert.assertEquals(1, reverse.getId().intValue());
		Assert.assertTrue(reverse.isCommentsEnabled());
		Assert.assertTrue(reverse.isEnabled());

		Assert.assertNotNull(reverse.getAuthor());
		Assert.assertNull(reverse.getAuthor().getName());
		Assert.assertEquals("signature", reverse.getAuthor().getSignature());
	}
}
