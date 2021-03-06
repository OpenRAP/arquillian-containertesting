package org.openrap.tdd.arquillian.jbossejb.facades.service;

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrap.tdd.arquillian.jbossejb.domain.AuctionItem;
import org.openrap.tdd.arquillian.jbossejb.facades.DAO;

@RunWith(Arquillian.class)
public class TestAuctionItemService {

    @Deployment
    public static Archive<?> createDeployment() throws Exception {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "a.war")
                .addClasses(DAO.class)
                .addClass(AuctionItemServiceAsAuctionParticipant.class)
                .addClass(AuctionItemServiceAsAuctionManager.class)
                .addClasses(AuctionItemService.class,ItemBidInterceptor.class)
                
                .addPackage("org.openrap.tdd.arquillian.jbossejb.domain")
                .addAsWebInfResource("jbossas-ds.xml")
                //.addAsWebInfResource("jboss-ejb3.xml") No security 
                .addAsResource(
                "META-INF/test-persistence.xml",
                "META-INF/persistence.xml")
                .addAsWebInfResource(
                EmptyAsset.INSTANCE,
                ArchivePaths.create("beans.xml"));

        System.out.println(war.toString(true));

        return war;
    }

    @EJB
    private AuctionItemService auctionItemService;
    
    @Test
    public void testAdd(){
        auctionItemService.add(new AuctionItem("testitem-AuctionManager",1));
    }
    
    
    @Test
    public void testAddAsAuctionManager(){
        auctionItemService.getAll();
    }

    
}
