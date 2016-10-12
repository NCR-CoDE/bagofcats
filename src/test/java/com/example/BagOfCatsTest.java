package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BagOfCatsTest {
 
  @Mock
  CatConsumer consumer;

  @Test
  public void shouldReturnNoCatsWhenBagContainsFewerCatsThanCapacity() {
    BagOfCats testee = createWithCapacityOf(1);
    assertThat(testee.add(aCat().build())).isEmpty();
  }
  
  @Test
  public void shouldReturnFirstAddedCatWhenBagCapacityExceeded() {
    BagOfCats testee = createWithCapacityOf(1);
    Cat first = aCat().withName("one").build();   
    testee.add(first);
    List<Cat> actual = testee.add(aCat().build());
    
    assertThat(actual).containsExactly(first);
  }
    
  @Test
  public void shouldReturnSecondAddedCatWhenBagCapacityExceedTwice() {
    BagOfCats testee = createWithCapacityOf(1);
    testee.add(aCat().build());
    
    Cat second = aCat().withName("two").build();
    testee.add(second);
    
    List<Cat> actual = testee.add(aCat().build());
    
    assertThat(actual).containsExactly(second);
  }  
  
  @Test
  public void shouldHonourTheRequestedCapacity() {
    BagOfCats testee = createWithCapacityOf(3);

    assertThat(testee.add(aCat().build())).isEmpty();
    assertThat(testee.add(aCat().build())).isEmpty();
    assertThat(testee.add(aCat().build())).isEmpty();
    
    assertThat(testee.add(aCat().build())).hasSize(1);
  }  
  
  @Test
  public void shouldInstantlyRejectCatsWithTooManyLegs() {
    BagOfCats testee = createBagWithLargeCapacity();
    
    Cat fiveLegged = aCat()
        .withLegs(5)
        .build();
    
    Cat sixLegged = aCat()
        .withLegs(5)
        .build();
    
    assertThat(testee.add(fiveLegged)).containsExactly(fiveLegged);
    assertThat(testee.add(sixLegged)).containsExactly(sixLegged); 
  }
  
  @Test
  public void shouldInstantlyRejectDeadCats() {
    BagOfCats testee = createBagWithLargeCapacity();
    
    Cat aDeadCat = aCat()
        .withIsAlive(false)
        .build();
    
    Cat anotherDeadCat = aCat()
        .withIsAlive(false).
        build();
    
    assertThat(testee.add(aDeadCat)).containsExactly(aDeadCat);
    assertThat(testee.add(anotherDeadCat)).containsExactly(anotherDeadCat); 
  }
  
  @Test
  public void shouldEjectAllCatsWhenGivenACatTravelingAtTheVelocityLimit() {
    int velocityLimit = 100;
    BagOfCats testee = createBagWithLargeCapacity();
    
    testee.add(aCat().build());
    testee.add(aCat().build());
    testee.add(aCat().build());
    
    Cat fastCat = aCat()
        .withVelocity(velocityLimit)
        .build();
    
    assertThat(testee.add(fastCat)).hasSize(3);
  }
  
  @Test
  public void shouldEjectAllCatsWhenGivenACatTravelingAboveTheVelocityLimit() {
    int velocityLimit = 100;
    BagOfCats testee = createBagWithLargeCapacity();
    
    testee.add(aCat().build());
    testee.add(aCat().build());
    testee.add(aCat().build());
    
    Cat fastCat = aCat()
        .withVelocity(velocityLimit + 1)
        .build();
    
    assertThat(testee.add(fastCat)).hasSize(3);
  }
  
  @Test
  public void shouldEjectFastCatWhenNewFastCatAdded() {
    int velocityLimit = 100;
    BagOfCats testee = createBagWithLargeCapacity();
    
    Cat firstFastCat = aCat()
        .withName("1st")
        .withVelocity(velocityLimit)
        .build();
    
    Cat secondFastCat = aCat()
        .withName("2nd")
        .withVelocity(velocityLimit + 1)
        .build();
    
    testee.add(aCat().build());
    testee.add(firstFastCat);
    
    List<Cat> actual = testee.add(secondFastCat);
    
    assertThat(actual).containsExactly(firstFastCat);
  }
  
  @Test
  public void shouldNotEjectAllCatsWhenGivenAFastCatBelowVelocityLimit() {
    BagOfCats testee = createBagWithLargeCapacity();
    
    testee.add(aCat().build());
    testee.add(aCat().build());
    testee.add(aCat().build());
    
    assertThat(testee.add(aCat().withVelocity(99).build())).isEmpty();
  }
  
  @Test
  public void shouldSendCatsCalledBorisToTheGreatConsumerWhichIsInNoWayCruel() {
    BagOfCats testee = createBagWithLargeCapacity();
    
    testee.add(aCat().withName("Boris").build());
    
    verify(consumer).consume(any(Cat.class));
  }
  
  @Test
  public void shouldSendCatsCalledNigelToTheGreatConsumerWhichIsInNoWayCruel() {
    BagOfCats testee = createBagWithLargeCapacity();
    
    testee.add(aCat().withName("Nigel").build());
    
    verify(consumer).consume(any(Cat.class));
  }
  
  @Test
  public void shouldNotRejectConsumedCats() {
    BagOfCats testee = createBagWithLargeCapacity();
    
    Cat nigel = aCat().withName("Nigel").build();
    
    assertThat(testee.add(nigel)).isEmpty();
  }
  
  @Test
  public void shouldNotSendCatsNotNamedNigelOrBorisToTheGreatConsumer() {
    BagOfCats testee = createBagWithLargeCapacity();
    
    testee.add(aCat().withName("Horis").build());
    testee.add(aCat().withName("Migel").build());  
    testee.add(aCat().withName("Frank").build());   
    
    verify(consumer, never()).consume(any(Cat.class));
  }
  
  @Test
  public void shouldPrepareCatsBeforeSendingThemToTheGreatConsumerWhichIsAlsoNotCruel() {
    BagOfCats testee = createWithCapacityOf(10);
    
    CatBuilder nigel = aCat()
        .withName("Nigel")
        .withIsAlive(true)
        .withLegs(4);
    
    testee.add(nigel.build()); 
    
    Cat prepared = nigel
        .withIsAlive(false)
        .withLegs(1)
        .build();
    
    verify(consumer).consume(prepared);
  }
  
  @Test
  public void shouldNotCountConsumedCatsTowardsCapacity() {
    BagOfCats testee = createWithCapacityOf(1);
    testee.add(aCat().withName("Nigel").build());
    assertThat(testee.add(aCat().build())).isEmpty();
  }

  private BagOfCats createBagWithLargeCapacity() {
    return createWithCapacityOf(1000);
  }
  
  private BagOfCats createWithCapacityOf(int capacity) {
    return new BagOfCats(consumer,capacity);
  }
  
  private CatBuilder aCat() {
    return new CatBuilder();
  }

}
