package com.hexlindia.drool.collection.business.impl;

import com.hexlindia.drool.collection.data.repository.api.CollectionRepository;
import com.hexlindia.drool.collection.dto.mapper.CollectionPostMapper;
import com.hexlindia.drool.post.data.repository.api.PostRepository;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CollectionImplTest {

    CollectionImpl collectionSpy;

    @Mock
    CollectionRepository collectionRepositoryMock;

    @Mock
    PostRepository postRepositoryMock;

    @Mock
    CollectionPostMapper collectionPostMapperMock;

    /*@BeforeEach
    void setUp() {
        this.collectionSpy = Mockito.spy(new CollectionImpl(collectionRepositoryMock, postRepositoryMock, collectionPostMapperMock));
    }

    @Test
    void getCollection_CollectionNotFound() {
        when(this.collectionRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(CollectionNotFoundException.class, () -> {
            collectionSpy.getCollectionFromRepository("1");
        });
    }

    @Test
    void getCollection_CollectionEntityReturned() {
        CollectionEntity collectionEntityMock = new CollectionEntity();
        collectionEntityMock.setId(101010);
        when(this.collectionRepositoryMock.findById(any())).thenReturn(Optional.of(collectionEntityMock));
        CollectionEntity collectionEntityReturned = collectionSpy.getCollectionFromRepository("1");
        assertEquals(101010, collectionEntityReturned.getId());
    }

    @Test
    void getPost_PostNotFound() {
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> {
            collectionSpy.getPost("1");
        });
    }

    @Test
    void getPost_PostEntityReturned() {
        PostEntity postEntityMock = new PostEntity();
        postEntityMock.setId(11111);
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(postEntityMock));
        PostEntity postEntityReturned = collectionSpy.getPost("1");
        assertEquals(11111, postEntityReturned.getId());
    }

    @Test
    void addPost_ExistingCollectionExecutionFlow() {
        CollectionEntity collectionMock = new CollectionEntity();
        collectionMock.setId(1);
        PostEntity postMock = new PostEntity();
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setCollectionId(String.valueOf("1"));
        collectionPostDto.setPostId("1");

        when(this.collectionRepositoryMock.findById(any())).thenReturn(Optional.of(collectionMock));
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(postMock));
        when(this.collectionRepositoryMock.save(any())).thenReturn(collectionMock);

        collectionSpy.addPost(collectionPostDto);

        verify(this.collectionSpy, times(1)).getCollectionFromRepository(any());
        verify(this.collectionRepositoryMock, times(1)).save(any());
    }

    @Test
    void addPost_NewCollectionExecutionFlow() {
        CollectionEntity collectionMock = new CollectionEntity();
        collectionMock.setId(51);
        PostEntity postMock = new PostEntity();
        CollectionPostDto collectionPostDtoMock = new CollectionPostDto();
        collectionPostDtoMock.setPostId("1");

        when(this.collectionPostMapperMock.toEntity(any())).thenReturn(collectionMock);
        when(this.collectionRepositoryMock.save(any())).thenReturn(collectionMock);
        when(this.postRepositoryMock.findById(any())).thenReturn(Optional.of(postMock));

        collectionSpy.addPost(collectionPostDtoMock);

        verify(this.collectionRepositoryMock, times(2)).save(any());
    }*/
}